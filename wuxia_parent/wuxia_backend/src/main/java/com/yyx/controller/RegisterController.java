package com.yyx.controller;

import com.yyx.entity.Result;
import com.yyx.utils.AliyunOSSUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequestMapping("/register")
@RestController
public class RegisterController {

    /**
     * 接受前端发送来的图片，并将其上传到阿里云OSS
     * @param imgFile: 是一个由前端传来的二进制数据
     * @return
     */
    @RequestMapping("upload")
    public Result upload(MultipartFile imgFile){
        //动态截取文件名后缀:
        String originalFilename = imgFile.getOriginalFilename(); // 原始文件名
        int index = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(index); // .jpg .png

        String fileName = UUID.randomUUID().toString(); //36位字符串
        fileName += extension;
        String dir = "userAvator_wuxia/";
        try {
            // 文件上传到阿里云OSS
            AliyunOSSUtil.upload(imgFile.getBytes(), dir + fileName);
            //将上传图片名称存入redis，基于redis的set集合存储
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "头像上传失败");
        }
        return new Result(true, "头像上传成功", fileName);
    }
}
