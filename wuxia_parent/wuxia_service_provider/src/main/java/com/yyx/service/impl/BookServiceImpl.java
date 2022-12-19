package com.yyx.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yyx.dao.BookDao;
import com.yyx.entity.PageResult;
import com.yyx.entity.QueryPageBean;
import com.yyx.pojo.Book;
import com.yyx.service.BookService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service(interfaceClass = BookService.class)
@Transactional
public class BookServiceImpl implements BookService {

    @Resource
    BookDao bookDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        //完成分页查询: 基于mybatis框架提供的分页助手插件
        PageHelper.startPage(currentPage, pageSize); //底层是基于线程绑定(ThreadLocal)来实现的,会自动在sql语句后加上limit '',''
        Page<Book> page = bookDao.findPage(); //page是分页助手完成的封装
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }
}
