package com.yyx.controller;

import com.yyx.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class testController {


    @RequestMapping("/haha")
    public Result test(Integer id){
        System.out.println(id);
        return new Result(true, "猪猪猪","我是猪");
    }
}
