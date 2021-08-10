package com.wmy.canteen.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String sno;
//    private String username;
    private String password;
    private String email;
    private String status;
    private String code;

    public User() {
    }

    public User(String sno, String password, String email) {
        this.sno = sno;
        this.password = password;
        this.email = email;
    }

    public User(String sno, String password, String email, String status, String code) {
        this.sno = sno;
        this.password = password;
        this.email = email;
        this.status = status;
        this.code = code;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
