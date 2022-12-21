import os
import re


def traverseFiles():
    lst = ['jinyong', 'gulong', 'liangyusheng']
    for au in lst:
        g = os.walk(fr"C:\Users\Administrator\Desktop\爬取小说\{au}")
        for path, dir_list, file_list in g:
            for file_name in file_list:
                url = os.path.join(path, file_name)  # C:\Users\Administrator\Desktop\爬取小说\gulong\七星龙王\1.html
                newHtml = change(url)
                with open(url, 'w', encoding='utf-8') as f:
                    print(url)
                    f.write(newHtml)

def getPreHtml(url):
    with open(url, 'r', encoding='utf-8') as f:
        html = f.read()
        obj = re.compile(r'(?P<preHtml>.*?)<div class="next">', re.S)
        result = obj.finditer(html)
        for it in result:
            return it.group('preHtml')


def change(url):
    buffer1 = '''	<div class="next">
		<a href="'''

    # javascript:alert('刚开始哦');  或者 ./{当前数字-1}.html

    buffer2 = '''">上一章</a>
		<a href="'''

    # javascript:alert('没有了哦');  或者 ./{当前数字+1}.html

    buffer3 = '''">下一章</a>
	</div>'''

    preHtml = getPreHtml(url)
    postHtml = '''</div>

                </body>
                
                </html>'''

    input1 = ''
    input2 = ''

    htmlNum = int(url.split('\\')[-1].split('.')[0])
    if htmlNum == 0:
        input1 = "javascript:alert('刚开始哦');"
    else:
        temp = htmlNum - 1
        input1 = f"./{temp}.html"

    obj = re.compile(r'(?P<pre>.*?)[0-9]*.html')
    result = obj.finditer(url)
    postUrl = ''
    tmp = htmlNum + 1
    for it in result:
        postUrl = it.group('pre') + str(tmp) + '.html'

    if os.path.exists(postUrl) is False:
        input2 = "javascript:alert('没有了哦');"
    else:
        temp = htmlNum + 1
        input2 = f"./{temp}.html"

    result = preHtml + buffer1 + input1 + buffer2 + input2 + buffer3 + postHtml
    return result


if __name__ == '__main__':
    traverseFiles()