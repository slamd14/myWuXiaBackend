package com.yyx.dao;

import com.yyx.pojo.User;

public interface LoginDao {
    public String getPwd(String account);

    public String getAvatar(String account);
}
