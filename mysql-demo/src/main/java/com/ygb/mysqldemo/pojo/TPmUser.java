package com.ygb.mysqldemo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 拍卖商城用户对象 t_pm_user
 *
 * @author ruoyi
 * @date 2022-04-27
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@TableName("t_pm_user")
public class TPmUser extends Model<TPmUser> implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String userName;

    private String phoneNumber;

    private Integer uId;


}
