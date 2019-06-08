package com.jkk.finances.Model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -7448027521233959770L;

    public User() {
    }

    public User(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Integer userId;
    private String userName;
}
