package com.yyx.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yyx.entity.Result;
import com.yyx.pojo.User;
import com.yyx.service.LoginService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequestMapping("/login")
@RestController
public class LoginController {

    @Reference
    LoginService loginService;

    /**
     * 登录校验
     * @param user
     * @return
     */
    @RequestMapping("login")
    public Result login(@RequestBody User user, HttpSession httpSession) {
        try {
            loginService.login(user);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
        // TODO 登录成功需要作缓存(session?)
        httpSession.setAttribute("account", user.getAccount());
        return new Result(true, "登陆成功");
    }

    @RequestMapping("getLoginInfo")
    public Result getLoginInfo(HttpSession httpSession) {
        String account = (String)httpSession.getAttribute("account");
        if (account == null) {
            return new Result(false, "当前未登录");
        }
        return new Result(true, "当前已经登录", account);
    }

    @RequestMapping("getAvatar")
    public Result getAvatar(String account) {
        String avatar = loginService.getAvatar(account);
        return new Result(true, "查询头像成功", avatar);
    }

    @RequestMapping("logout")
    public Result logout(HttpSession httpSession) {
        /**
         * removeAttribute就是从session删除指定名称的绑定对象，也就是说调用此方法后再调用getAttribute(String name)时，不能获取指定名称的绑定对象，但是session还存在。invalidate就是销毁此session对象，session对象中绑定的那些对象值也都不存在了
         */
        httpSession.invalidate();
//        httpSession.removeAttribute("account");
        return new Result(true, "退出登录成功");
    }
}
