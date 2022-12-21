import os
import re

buffer1 = '''<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>'''

# 雪山飞狐-第一章

buffer2 = '''</title>
	<link rel="stylesheet" href="../../../css/bookContent.css">
</head>

<body>

<div id="container">

	<div class="topnav">
'''

# 		<h1>雪山飞狐</h1>
# 		<h2>第一章</h2>

buffer3 = '''	</div>

	<div class="text">
'''

# 书籍内容

buffer4 = '''	</div>

	<div class="next">
		<a href="javascript:alert('刚开始哦');">上一章</a>
		<a href="jinyong/xueshanfeihu/2.html">下一章</a>
	</div>



</div>

</body>

</html>'''


def getbookContent(url):
    with open(url, 'r', encoding='utf-8') as f:
        html = f.read()
        obj = re.compile(r'<div class="text">(?P<bookContent>.*?)</div>', re.S)
        result = obj.finditer(html)
        for it in result:
            return it.group('bookContent')

def getTitle(url):
    with open(url, 'r', encoding='utf-8') as f:
        html = f.read()
        obj = re.compile(r'<title>(?P<title>.*?)</title>', re.S)
        result = obj.finditer(html)
        for it in result:
            return it.group('title')

def getResult(bookContent, title):
    h1 = title.split('-')[0]
    h2 = title.split('-')[1]
    h1h2 = f'<h1>{h1}</h1>' \
           f'<h2>{h2}</h2>'
    return buffer1 + title + buffer2 + h1h2 + buffer3 + bookContent + buffer4

def traverseFiles():
    lst = ['jinyong', 'gulong', 'liangyusheng']
    for au in lst:
        g = os.walk(fr"C:\Users\Administrator\Desktop\爬取小说\{au}")
        for path, dir_list, file_list in g:
            for file_name in file_list:
                url = os.path.join(path, file_name)  # C:\Users\Administrator\Desktop\爬取小说\gulong\七星龙王\1.html
                bookContent = getbookContent(url)
                title = getTitle(url)
                newBookHtml = getResult(bookContent, title)
                with open(url, 'w', encoding='utf-8') as f:
                    print(url)
                    f.write(newBookHtml)


if __name__ == '__main__':
    traverseFiles()