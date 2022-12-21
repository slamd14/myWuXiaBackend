import pymysql
import os
import re


def connect_mysql():
    """
    数据库连接
    :return:
    """
    db = pymysql.connect(host='localhost', user='root', password='yyx',
                         port=3306, db='mywuxia')
    return db


def insertIntoMysql(id, categoryName, bookId, author, book, num):
    """
    数据库插入操作
    :param id:
    :param categoryName:
    :param bookId:
    :param author:
    :param book:
    :param num:
    :return:
    """
    categoryUrl = f'../bookContent/{author}/{book}/{num}.html'
    db = connect_mysql()
    cursor = db.cursor()
    sql = "INSERT INTO category VALUES (%(id)s, %(categoryName)s, %(bookId)s, %(categoryUrl)s)"
    param = {
        'id': id,
        'categoryName': categoryName,
        'bookId': bookId,
        'categoryUrl': categoryUrl
    }
    cursor.execute(sql, param)
    db.commit()
    print(f'{id}, {categoryName}, {bookId}, {categoryUrl}')


def fixDirectoryBug():
    """
    将文件夹名字都修改为相应拼音
    :return:
    """
    lst = ['jinyong', 'gulong', 'liangyusheng']
    pinyinName = []
    for au in lst:
        g = os.walk(fr"C:\Users\Administrator\Desktop\爬取小说\{au}")
        for path, dir_list, file_list in g:
            for file_name in file_list:
                url = os.path.join(path, file_name)  # C:\Users\Administrator\Desktop\爬取小说\gulong\七星龙王\1.html
                bookName = getEngName(url)
                pinyinName.append(bookName)
                break

    count = 0
    for au in lst:
        g = os.walk(fr"C:\Users\Administrator\Desktop\爬取小说\{au}")
        for path, dir_list, file_list in g:
            for dir_name in dir_list:
                dir = os.path.join(path, dir_name)
                newDir = "C:\\Users\\Administrator\\Desktop\\爬取小说" + f"\\{au}\\" + f"{pinyinName[count]}"
                os.rename(dir, newDir)
                count += 1


def traverseFiles():
    """
    遍历所有文件夹以及文件夹下文件
    :return:
    """
    lst = ['jinyong', 'gulong', 'liangyusheng']
    count = 1  # 数据库中的id
    urls = []
    for au in lst:
        g = os.walk(fr"C:\Users\Administrator\Desktop\爬取小说\{au}")
        for path, dir_list, file_list in g:
            for file_name in file_list:
                url = os.path.join(path, file_name)  # C:\Users\Administrator\Desktop\爬取小说\gulong\七星龙王\1.html
                urls.append(url)
    # 为了让每本书的目录有序, 因为上面目录遍历的结果并不是很有序
    urls.sort(key=len)
    for url in urls:
        id = count
        # 从html文件中提取categoryName
        categoryName = getCategoryName(url)
        bookId = getBookId(url)
        # 数据库中查询不到该书的id(目前book表中还没存放该书)
        if bookId == -1:
            lst = url.split('\\')
            bookChName = lst[-2]
            print(f'book表中暂时没有<<{bookChName}>>')
            continue
        # 从url中提取author
        author = getAuthor(url)
        # 从html文件中提取书名的英文
        book = getEngName(url)
        # 从url中提取数字作为num
        num = getNum(url)
        insertIntoMysql(id, categoryName, bookId, author, book, num)
        count += 1


def getCategoryName(url):
    content = ''
    with open(url, 'r', encoding='utf-8') as f:
        content = f.read()
    obj = re.compile(r'<title>.*-(?P<categoryName>.*?)</title>')
    result = obj.finditer(content)
    for it in result:
        return it.group('categoryName')


def getBookId(url):
    lst = url.split('\\')
    bookName = lst[-2]
    # 查询数据库
    db = connect_mysql()
    cursor = db.cursor()
    sql = f"SELECT id FROM book WHERE bookName = '{bookName}'"
    cursor.execute(sql)
    bookId = cursor.fetchone()
    if bookId is None:
        return -1
    return bookId[0]


def getAuthor(url):
    lst = url.split('\\')
    author = lst[-3]
    return author


def getEngName(url):
    content = ''
    with open(url, 'r', encoding='utf-8') as f:
        content = f.read()
    obj = re.compile(r'<h2>.*?<a href="/book/(?P<bookName>.*?).html">', re.S)
    result = obj.finditer(content)
    for it in result:
        return it.group('bookName')


def getNum(url):
    lst = url.split('\\')
    post = lst[-1]
    num = post.split('.')[0]
    return num


if __name__ == '__main__':
    # traverseFiles()
    fixDirectoryBug()