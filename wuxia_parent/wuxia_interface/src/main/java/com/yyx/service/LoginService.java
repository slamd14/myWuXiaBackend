package com.yyx.service;

import com.yyx.pojo.User;

public interface LoginService {
    public void login(User user);

    public String getAvatar(String account);
}
