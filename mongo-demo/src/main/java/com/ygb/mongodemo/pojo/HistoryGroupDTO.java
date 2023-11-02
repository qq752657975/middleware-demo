package com.ygb.mongodemo.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 群聊天实体类
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2022/6/23 15:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HistoryGroupDTO {

    /**主键*/
    private String id;
    /**聊天服务器主键*/
    private String msgId;

    /**群Id*/
    private String groupId;

    /**群号*/
    private String groupNumber;

    /**发消息用户id*/
    private String memberId;

    /**发消息用户头像*/
    private String memberHeadImg;

    /**发消息用户昵称*/
    private String memberNick;

    /**发消息用户账号*/
    private String memberAccount;

    /**发消息用户电话*/
    private String memberPhone;

    /**发消息用户备注*/
    private String memberRemark;

    /**消息内容*/
    private String msg;

    /**发送时间*/
    private String createdTime;

    /**消息类型(0,文字、1,图片、2,语言、3,红包、4,视频、5,链接、6,转账、7、地理位置 8、名片 9、自焚消息  10、引用消息  1000,系统消息 1001,撤回消息  */
    private Integer  type;

    /**消息类型(0,文字、1,图片、2,语言、3,红包、4,视频、5,链接、6,转账、7、地理位置 8、名片 9、自焚消息  10、引用消息  1000,系统消息 1001,撤回消息  不是我发的+500开始*/
    private Integer cellType;

    /**是否是我发送 1.我 2.不是 3.系统消息(撤回消息)*/
    private Integer isMe;

    /**已读未读 1.未读 2.已读*/
    private Integer isRead;

    /**发送状态 0.发送中 1.发送成功 2.发送失败 3.自焚消息摧毁*/
    private Integer status;

    /**红包状态 0.不是红包消息 1.未领取 2.已领取 3.退回*/
    private Integer redEnvelopeStatus;

    /**红包id*/
    private String redEnvelopeId;

    /**是否撤回 1.否 2.撤回*/
    private Integer withdraw;

    /**登录人主键*/
    private String loginMemberId;


}
