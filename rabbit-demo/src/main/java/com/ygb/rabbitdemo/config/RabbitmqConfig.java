package com.ygb.rabbitdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2022/7/18 13:47
 */
@Configuration
public class RabbitmqConfig {

    @Bean
    public Exchange exchangeOrderOverrunTime() {
        Map<String, Object> props = new HashMap<>(16);
        props.put("x-delayed-type", ExchangeTypes.DIRECT);
        return new CustomExchange("testEx.delayed", "x-delayed-message", true, false, props);
    }

    @Bean
    public Queue queueOrderOverrunTime() {
        return new Queue("testQueue.delayed", true, false, false, null);
    }

    @Bean
    public Binding bindingOrderOverrunTime() {
        return BindingBuilder.bind(queueOrderOverrunTime()).to(exchangeOrderOverrunTime()).with("testKey.delayed").noargs();
    }
}
