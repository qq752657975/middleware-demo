package com.ygb.dubboconsumption.consumer;

import com.ygb.duibbointerface.DemoService;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试duboo基础用法
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2022/7/30 19:01
 */
@RestController
public class DemoConsumer {



    @DubboReference(retries = 0,timeout = 1000)
    private DemoService demoService;

    @GetMapping("/demo")
    public String seyHello(@RequestParam String name){
        return demoService.sayHello(name);
    }


}
