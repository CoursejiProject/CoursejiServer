package com.littlecorgi.springtest;

import com.littlecorgi.springtest.service.User;
import com.littlecorgi.springtest.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author littlecorgi
 * @date 2021/4/10
 */
public class Main {

    /**
     * 主函数
     *
     * @param args 运行参数
     */
    public static void main(String[] args) {
        // 创建一个Spring的IoC容器实例，然后加载配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        // 获取Bean:
        UserService userService = context.getBean(UserService.class);
        // 正常调用:
        User user = userService.login("bob@example.com", "password");
        // 打印
        System.out.println(user.getName());
    }
}
