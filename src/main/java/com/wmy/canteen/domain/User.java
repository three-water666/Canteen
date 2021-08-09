package com.wmy.canteen.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String sno;
//    private String username;
    private String password;
    private String email;

    public User() {
    }

    public User(String sno, String password, String email) {
        this.sno = sno;
        this.password = password;
        this.email = email;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
