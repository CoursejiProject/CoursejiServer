package com.littlecorgi.springtest;

import com.littlecorgi.springtest.service.User;
import com.littlecorgi.springtest.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 注解配置的启动类
 *
 * @author littlecorgi
 * @date 2021/4/10
 */
@Configuration // 代表是配置类
@ComponentScan // 它告诉容器，自动搜索当前类所在的包以及子包，把所有标注为@Component的Bean自动创建出来，并根据@Autowired进行装配
public class AppConfig {
    /**
     * 启动函数
     *
     * @param args 配置参数
     */
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        User user = userService.login("bob@example.com", "password");
        System.out.println(user.getName());
    }
}
