package com.yyx.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String account;
    private String password;
    private String mail;
    private String phone;
    private String avatar;

    public User(String id, String account, String password, String mail, String phone, String avatar) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.mail = mail;
        this.phone = phone;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
