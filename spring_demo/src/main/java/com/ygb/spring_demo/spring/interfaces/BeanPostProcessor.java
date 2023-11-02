package com.ygb.spring_demo.spring.interfaces;

/**
 * @Author ygb
 * @Version 1.0
 * @Date 2023-07-30 21:46
 */
public interface BeanPostProcessor {

    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
