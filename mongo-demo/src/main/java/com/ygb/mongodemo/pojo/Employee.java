package com.ygb.mongodemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * emp集合对应类
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2022/6/3 15:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("emp")
public class Employee {

    @Id //映射文档中的_id
    private Integer id;

    @Field("username")
    private String name;

    @Field
    private int age;

    @Field
    private Double salary;

    @Field
    private Date birthday;
}
