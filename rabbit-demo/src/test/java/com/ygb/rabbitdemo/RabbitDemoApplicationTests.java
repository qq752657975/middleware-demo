package com.ygb.rabbitdemo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class RabbitDemoApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {

        rabbitTemplate.convertAndSend("testEx.delayed", "testKey.delayed",
                String.valueOf("test123").getBytes(StandardCharsets.UTF_8), properties -> {
                    properties.getMessageProperties().setDelay(30000);
                    properties.getMessageProperties().setAppId("test123");
                    properties.getMessageProperties().setContentEncoding(StandardCharsets.UTF_8.toString());
                    properties.getMessageProperties().setMessageId("test123");
                    return properties;
                }, new CorrelationData("test123"));
    }

}
