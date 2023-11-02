package com.ygb.mongodemo.pojo;

import lombok.*;

/**
 * k:v键值队对象
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2022/6/24 13:43
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UniversalKeyValue {

    /**key*/
    private String universalKey;

    /**valyhe*/
    private String universalValue;
}
