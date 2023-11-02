package com.ygb.spring_demo;

import com.ygb.spring_demo.service.AppConfig;
import com.ygb.spring_demo.service.UserInterface;
import com.ygb.spring_demo.spring.YgbApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * @Author ygb
 * @Version 1.0
 * @Date 2023-07-30 22:20
 */
public class Test {

    public static void main(String[] args) {

        // 扫描--->创建单例Bean BeanDefinition BeanPostPRocess
        YgbApplicationContext applicationContext = new YgbApplicationContext(AppConfig.class);

        UserInterface userService = (UserInterface) applicationContext.getBean("userService");
        userService.test();
    }
}
