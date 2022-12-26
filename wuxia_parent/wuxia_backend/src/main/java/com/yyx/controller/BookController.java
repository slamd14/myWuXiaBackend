package com.yyx.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yyx.entity.PageResult;
import com.yyx.entity.QueryPageBean;
import com.yyx.entity.Result;
import com.yyx.pojo.Book;
import com.yyx.pojo.Category;
import com.yyx.pojo.Comment;
import com.yyx.service.BookService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/book")
@RestController
public class BookController {

    @Reference
    BookService bookService;

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return bookService.findPage(queryPageBean);
    }

    /**
     * 根据bookId查询book
     *
     * @param bookId
     * @return
     */
    @RequestMapping("/getBook")
    public Result getBook(Integer bookId) {
        Book book = bookService.getBook(bookId);
        return new Result(true, "getBookOKOK!", book);
    }

    /* 根据bookId查询目录*/
    @RequestMapping("/getBookCategories")
    public Result getBookCategories(Integer bookId) {
        List<Category> bookCategories = bookService.getBookCategories(bookId);
        return new Result(true, "getBookCategoriesOKOK!", bookCategories);
    }

    /* 根据bookId查询评论 */
    @RequestMapping("/getBookComments")
    public Result getBookComments(Integer bookId) {
        List<Comment> bookComments = bookService.getBookComments(bookId);
        return new Result(true, "getBookCommentsOKOK!", bookComments);
    }
}