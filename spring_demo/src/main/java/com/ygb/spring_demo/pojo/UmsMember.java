package com.ygb.spring_demo.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员实体类
 * @author yang
 * @version 1.0
 * @date 2021/7/21 2:07 下午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UmsMember  implements Serializable {

    private static final long serialVersionUID = -5794643224351819281L;
    /**主键*/
    private Long memberId ;
    /** 用户手机 */
    private String memberPhone ;
    /** 用户密码 */
    private String memberPassword ;
    /** 用户昵称 */
    private String memberNick ;
    /** 用户头像 */
    private String memberHeadImg ;
    /** 用户邮箱 */
    private String memberEmil ;
    /** 用户生日 */
    private Date memberBirthday ;
    /** 用户性别;0,男、1,女、2,保密 */
    private Integer memberSex ;
    /** 用户账号 */
    private String memberAccount;
    /** 用户二维码 */
    private String memberQrCode ;
    /** 用户签名 */
    private String memberSignature ;
    /** 会员类型;0,普通,1,会员,2,超级会员 */
    private Integer memberVipType ;
    /** 开始时间 */
    private Date memberVipStartTime ;
    /** 结束时间 */
    private Date memberVipEndTime ;
    /** 用户状态;1,启用、0,禁用、3,注销 */
    private Integer memberStatus ;
    /**禁用时间*/
    private Date disableTime;
    /** 支付密码 */
    private String memberPayPassword ;
    /**修改账号时间*/
    private Date updateAccountTime;
    /** 逻辑删除;0,删除、1,不删除 */
    private Integer deleteFlag ;
    /** 乐观锁 */
    private Integer revision ;
    /** 创建时间 */
    private Date createdTime ;
    /**地区(后期废弃)*/
    private String abandonedAddress;
    /**上级主键*/
    private Long parentId;
    /**上上级主键*/
    private Long grandpaId;
    /**邀请码*/
    private String inviteCode;
    /**是否团队长 0 否 1是*/
    private Integer teamLeader;
    /**团队主键*/
    private Long teamId;
}
