package com.yyx.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yyx.dao.LoginDao;
import com.yyx.dao.RegisterDao;
import com.yyx.pojo.User;
import com.yyx.service.LoginService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@Service(interfaceClass = LoginService.class)
@Transactional
public class LoginServiceImpl implements LoginService{

    @Resource
    LoginDao loginDao;
    @Resource
    RegisterDao registerDao;

    @Override
    public void login(User user) {
        String account = user.getAccount().replace(" ", "");
        String pwd = user.getPassword().replace(" ", "");
        // 校验用户是否存在 -> 存在，则查询出密码校验；不存在，则抛出异常
        Integer nums = registerDao.judgeAccount(account);
        if (nums <= 0 ) {
            throw new RuntimeException("用户不存在!");
        }
        String dbPwd = loginDao.getPwd(account);
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        if (!dbPwd.equals(pwd)) {
            throw new RuntimeException("密码错误!");
        }
    }

    @Override
    public String getAvatar(String account) {
        return loginDao.getAvatar(account);
    }
}
