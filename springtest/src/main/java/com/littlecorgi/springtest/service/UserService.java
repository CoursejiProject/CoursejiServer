package com.littlecorgi.springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author littlecorgi
 * @date 2021/4/10
 */
@Component
public class UserService {
    @Autowired
    private MailService mailService;

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    private final List<User> users = new ArrayList<>(List.of(
            // users:
            new User(1, "bob@example.com", "password", "Bob"), // bob
            new User(2, "alice@example.com", "password", "Alice"), // alice
            new User(3, "tom@example.com", "password", "Tom") // tom
    ));

    /**
     * 登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return 登录后的用户数据
     */
    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                mailService.sendLoginMail(user);
                return user;
            }
        }
        throw new RuntimeException("login failed.");
    }

    public User getUser(long id) {
        return this.users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow();
    }

    /**
     * 注册
     *
     * @param email    邮箱
     * @param password 密码
     * @param name     用户名称
     * @return 用户数据
     */
    public User register(String email, String password, String name) {
        users.forEach((user) -> {
            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new RuntimeException("email exist.");
            }
        });
        User user = new User(
                users.stream().mapToLong(u -> u.getId()).max().getAsLong() + 1,
                email, password, name
        );
        users.add(user);
        mailService.sendRegistrationMail(user);
        return user;
    }
}
