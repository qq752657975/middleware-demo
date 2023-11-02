package com.ygb.spring_demo.conf;

import com.ygb.spring_demo.pojo.TPmUser;
import com.ygb.spring_demo.pojo.UmsMember;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author ygb
 * @Version 1.0
 * @Date 2023-07-30 21:25
 */
@Configuration
public class DemoConfig {

    @Bean
    public UmsMember umsMember(){
        return new UmsMember();
    }

    @Bean
    public TPmUser tPmUser(){
        UmsMember umsMember = umsMember();
        System.out.println(umsMember);
        return new TPmUser();
    }

    @Bean
    public TPmUser tPmUser1(){
        UmsMember umsMember = umsMember();
        System.out.println(umsMember);
        return new TPmUser();
    }


}
