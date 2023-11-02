package com.ygb.esdemo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * ES測試类
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023/1/8 21:15
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("es_data_demo")
public class EsDataDemo extends Model<EsDataDemo> implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer esId;

    private Integer number;
}
