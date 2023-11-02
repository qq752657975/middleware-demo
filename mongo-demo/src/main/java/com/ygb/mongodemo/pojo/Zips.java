package com.ygb.mongodemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * TODO
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2022/6/4 9:49
 */
@Document("zips")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zips {

    @Id
    private String id;

    @Field
    private String city;

    @Field
    private Double[] loc;

    @Field
    private Integer pop;

    @Field
    private String state;
}
