package com.ygb.spring_demo.service;

import com.ygb.spring_demo.spring.annotation.Autowired;
import com.ygb.spring_demo.spring.annotation.Component;
import com.ygb.spring_demo.spring.interfaces.BeanNameAware;

/**
 * @Author ygb
 * @Version 1.0
 * @Date 2023-07-30 22:14
 */
@Component
public class UserService implements UserInterface, BeanNameAware {

    @Autowired
    private OrderService orderService;

    @YgbValue("xxx")
    private String test;


    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void test() {
        System.out.println(beanName);
    }
}
