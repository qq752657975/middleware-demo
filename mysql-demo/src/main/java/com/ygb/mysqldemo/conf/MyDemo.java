package com.ygb.mysqldemo.conf;

import com.ygb.mysqldemo.pojo.TPmUser;
import com.ygb.mysqldemo.pojo.TpmUserDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author ygb
 * @Version 1.0
 * @Date 2023-07-30 21:21
 */
@Configuration
public class MyDemo {

    @Bean
    public TPmUser tPmUser(){
        return new TPmUser();
    }

    public TpmUserDate tpmUserDate(){
        TPmUser tPmUser = tPmUser();
        System.out.println(tPmUser);
        return new TpmUserDate();
    }

    public TpmUserDate tpmUserDate1(){
        TPmUser tPmUser = tPmUser();
        System.out.println(tPmUser);
        return new TpmUserDate();
    }
}
