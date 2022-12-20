package com.yyx.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yyx.dao.BookDao;
import com.yyx.entity.PageResult;
import com.yyx.entity.QueryPageBean;
import com.yyx.entity.Result;
import com.yyx.pojo.Book;
import com.yyx.pojo.Category;
import com.yyx.pojo.Comment;
import com.yyx.service.BookService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(interfaceClass = BookService.class)
@Transactional
public class BookServiceImpl implements BookService {

    @Resource
    BookDao bookDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //完成分页查询: 基于mybatis框架提供的分页助手插件
        PageHelper.startPage(currentPage, pageSize); //底层是基于线程绑定(ThreadLocal)来实现的,会自动在sql语句后加上limit '',''
        Page<Book> page = null;
        if ("recommend".equals(queryString)) {
            // 对推荐书籍作分页查询
            page = bookDao.findBookRecommended();
        } else {
            // 对book按作者进行分页查询
            if("jinyong".equals(queryString)) {
                queryString = "金庸";
            }
            if("wenruian".equals(queryString)) {
                queryString = "温瑞安";
            }
            if("liangyusheng".equals(queryString)) {
                queryString = "梁羽生";
            }
            if("gulong".equals(queryString)) {
                queryString = "古龙";
            }
            page = bookDao.findBookByAuthor(queryString);
        }
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Book getBook(Integer bookId) {
        return bookDao.getBook(bookId);
    }

    @Override
    public List<Category> getBookCategories(Integer bookId) {
        return bookDao.getBookCategories(bookId);
    }

    @Override
    public List<Comment> getBookComments(Integer bookId) {
        return bookDao.getBookComments(bookId);
    }
}
