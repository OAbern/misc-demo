package com.zhubajie.oabern.mock.demo.entity;

/**
 * Created by fengdi on 2016/8/9.
 */
public class User {
    private String userId;
    private String userName;
    private byte gender;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }
}
