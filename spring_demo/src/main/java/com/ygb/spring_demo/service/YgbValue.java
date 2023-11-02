package com.ygb.spring_demo.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author ygb
 * @Version 1.0
 * @Date 2023-07-30 22:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface YgbValue {

    String value() default "";
}
