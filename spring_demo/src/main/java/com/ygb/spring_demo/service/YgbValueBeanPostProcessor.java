package com.ygb.spring_demo.service;

import com.ygb.spring_demo.spring.interfaces.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * @Author ygb
 * @Version 1.0
 * @Date 2023-07-30 22:19
 */
public class YgbValueBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {

        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(YgbValue.class)) {
                field.setAccessible(true);
                try {
                    field.set(bean, field.getAnnotation(YgbValue.class).value());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // bean
        return bean;
    }
}
