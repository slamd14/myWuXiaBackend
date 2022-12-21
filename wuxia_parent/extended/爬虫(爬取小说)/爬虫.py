import os
import time

import requests
import asyncio
import aiohttp
import aiofiles
import re

base_url = 'http://wuxia.net.cn'
author_url = 'http://www.wuxia.net.cn/author/jinyong.html'
book_url = 'http://www.wuxia.net.cn/book/feihuwaizhuan.html'
category_url = 'http://www.wuxia.net.cn/book/xueshanfeihu/1.html'

authors = ['jinyong', 'gulong', 'liangyusheng']  # 没有温瑞安是因为该网站没有温瑞安的书...


def get_content(url):
    resp = requests.get(url)
    resp.encoding = 'utf-8'
    return resp.text


def go_author(author):
    """
    返回该作者所有书籍的url
    :param author:
    :return: 书籍与url的dic
    """
    books_urls = {}
    author_url = 'http://www.wuxia.net.cn/author/' + author + '.html'
    author_content = get_content(author_url)
    obj1 = re.compile(r'<ul class="co3">(?P<books>.*?)</ul>', re.S)
    result1 = obj1.finditer(author_content)
    for it in result1:
        temp = it.group('books')
        obj2 = re.compile(r'<li><a href="(?P<href>.*?)">(?P<bookName>.*?)</a></li>', re.S)
        result2 = obj2.finditer(temp)
        for it2 in result2:
            href = it2.group('href')  # /book/feihuwaizhuan.html
            href = base_url + href
            bookName = it2.group('bookName')
            # 去掉部分book前后的<strong>与</strong>标签
            if '<strong>' in bookName:
                obj3 = re.compile(r'<strong>(?P<newBookName>.*?)</strong>', re.S)
                result3 = obj3.finditer(bookName)
                for it3 in result3:
                    bookName = it3.group('newBookName')
            books_urls[bookName] = href
    return books_urls


async def go_book(book_name, book_url, author):
    """
    对某本书的目录进行操作
    :param book_name:
    :param book_url:
    :param author:
    :return:
    """
    resp = requests.get(book_url)
    obj1 = re.compile(r'<dd><a href="(?P<href>.*?)">(?P<cate_name>.*?)</a></dd>')
    result1 = obj1.finditer(resp.text)
    tasks = list()
    count = 1
    for it1 in result1:
        cate_url = base_url + it1.group('href')
        cate_name = it1.group('cate_name')
        # 准备异步任务
        tasks.append(asyncio.create_task(aio_download(cate_url, cate_name, book_name, author, count)))
        count += 1
    await asyncio.wait(tasks)


async def aio_download(url, cate_name, book_name, author, count):
    """
    对于每个任务(每个目录)的操作
    :param url:
    :param cate_name:
    :param book_name:
    :param count: 用于文件夹中目录按名字自动排序
    :return:
    """
    async with aiohttp.ClientSession() as session:
        async with session.get(url) as resp:
            print(f'正在爬取{book_name}的 {cate_name}')
            text = await resp.text()  # 异步get请求
            async with aiofiles.open(f'./{author}/{book_name}/{count}.html', 'w', encoding='utf-8') as f:
                await f.write(text)  # 异步文件io


def mkdir(path):
    folder = os.path.exists(path)
    if not folder:  # 判断是否存在文件夹如果不存在则创建为文件夹
        os.makedirs(path)
        return False
    else:
        return True


if __name__ == '__main__':
    for author in authors:
        dic = go_author(author)
        for book_name in dic:
            if mkdir(f'./{author}/{book_name}'):
                print(f'./{author}/{book_name} 已经存在，不需要再爬取!!!')
                continue
            book_url = dic[book_name]
            asyncio.run(go_book(book_name, book_url, author))
 