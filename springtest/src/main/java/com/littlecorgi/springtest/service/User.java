package com.littlecorgi.springtest.service;

/**
 * @author littlecorgi
 * @date 2021/4/10
 */
public class User {
    long id;
    String email;
    String password;
    String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 构造方法
     *
     * @param id       用户ID
     * @param email    邮箱
     * @param password 密码
     * @param name     用户名称
     */
    public User(long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
