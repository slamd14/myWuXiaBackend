package com.yyx.service;

import com.yyx.entity.PageResult;
import com.yyx.entity.QueryPageBean;
import com.yyx.entity.Result;
import com.yyx.pojo.Book;
import com.yyx.pojo.Category;
import com.yyx.pojo.Comment;

import java.util.List;

public interface BookService {

    public PageResult findPage(QueryPageBean queryPageBean);

    public Book getBook(Integer bookId);

    public List<Category> getBookCategories(Integer bookId);

    public List<Comment> getBookComments(Integer bookId);
}
