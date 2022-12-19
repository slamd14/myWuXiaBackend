package com.yyx.dao;

import com.github.pagehelper.Page;
import com.yyx.pojo.Book;

public interface BookDao {
    public Page<Book> findPage();
}
