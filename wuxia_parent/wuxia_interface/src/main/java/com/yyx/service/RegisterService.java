package com.yyx.service;

import com.yyx.pojo.User;

public interface RegisterService {

    public void register(User user);

    public void judgeAccount(String account);
}
