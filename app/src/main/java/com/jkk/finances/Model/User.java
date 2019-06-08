package com.jkk.finances.Model;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -7448027521233959770L;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
}
