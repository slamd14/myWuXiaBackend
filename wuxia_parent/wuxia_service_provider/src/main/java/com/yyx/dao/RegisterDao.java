package com.yyx.dao;

import com.yyx.pojo.User;

public interface RegisterDao {
    public void register(User user);

    public Integer judgeAccount(String account);
}
