package com.ygb.spring_demo.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 拍卖商城用户对象 t_pm_user
 *
 * @author ruoyi
 * @date 2022-04-27
 */

@Getter
@Setter
public class TPmUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String userName;

    private String phoneNumber;

    private Integer uId;


}
