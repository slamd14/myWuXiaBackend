package com.yyx.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yyx.dao.RegisterDao;
import com.yyx.pojo.User;
import com.yyx.service.RegisterService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.UUID;

@Service(interfaceClass = RegisterService.class)
@Transactional
public class RegisterServiceImpl implements RegisterService {

    @Resource
    RegisterDao registerDao;

    @Override
    public void register(User user) {
        String account = user.getAccount().replace(" ", "");
        judgeAccount(account);
        String password = user.getPassword().replace(" ","");
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        String mail = user.getMail().replace(" ","");
        String phone = user.getPhone().replace(" ","");
        String avatar = user.getAvatar();
        //1.UUID生成32位数
        String uuid32 = UUID.randomUUID().toString().replace("-", "");
        //2.然后截取前面或后面16位
        String id = uuid32.substring(0, 16);
        User newUser = new User(id, account, password, mail, phone, avatar);
        registerDao.register(newUser);
    }

    @Override
    public void judgeAccount(String account) {
        account = account.replace(" ", "");
        Integer num = registerDao.judgeAccount(account);
        if (num >= 1) {
            throw new RuntimeException("用户名已经存在");
        }
    }
}
