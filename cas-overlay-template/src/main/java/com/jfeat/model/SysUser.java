package com.jfeat.model;

public class SysUser {
    Long id;
    String password;
    String salt;
    String account;
    String name;
    String phone;
    String email;
    int  registeredPhone;
    int  registeredEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRegisteredPhone() {
        return registeredPhone;
    }

    public void setRegisteredPhone(int registeredPhone) {
        this.registeredPhone = registeredPhone;
    }

    public int getRegisteredEmail() {
        return registeredEmail;
    }

    public void setRegisteredEmail(int registeredEmail) {
        this.registeredEmail = registeredEmail;
    }
}
