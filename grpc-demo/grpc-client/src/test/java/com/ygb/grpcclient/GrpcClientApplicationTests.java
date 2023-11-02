package com.ygb.grpcclient;

import com.ygb.grpcclient.service.GrpcClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GrpcClientApplicationTests {

    @Autowired
    private GrpcClientService grpcClientService;

    @Test
    void contextLoads() {

        String s = grpcClientService.sendMessage("123456");

        System.out.println(s);
    }

}
