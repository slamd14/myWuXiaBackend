package com.yyx.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yyx.entity.PageResult;
import com.yyx.entity.QueryPageBean;
import com.yyx.service.BookService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/book")
@RestController
public class BookController {

    @Reference
    BookService bookService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return bookService.findPage(queryPageBean);
    }
}
