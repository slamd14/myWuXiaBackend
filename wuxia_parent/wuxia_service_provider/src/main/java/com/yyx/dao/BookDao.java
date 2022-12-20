package com.yyx.dao;

import com.github.pagehelper.Page;
import com.yyx.pojo.Book;
import com.yyx.pojo.Category;
import com.yyx.pojo.Comment;

import java.util.List;

public interface BookDao {
    public Page<Book> findBookRecommended();

    public Page<Book> findBookByAuthor(String query);

    public Book getBook(Integer id);

    public List<Category> getBookCategories(Integer id);

    public List<Comment> getBookComments(Integer id);
}
