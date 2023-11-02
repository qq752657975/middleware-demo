package com.ygb.mongodemo.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * TODO
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2022/6/24 9:52
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document("history_group")
public class HistoryGroup {

    /**主键*/
    @Id
    private String id;
    /**聊天服务器主键*/
    @Field
    private String msgId;

    /**群Id*/
    @Field
    private String groupId;

    /**群号*/
    @Field
    private String groupNumber;

    /**发消息用户id*/
    @Field
    private String memberId;

    /**发消息用户头像*/
    @Field
    private String memberHeadImg;

    /**发消息用户昵称*/
    @Field
    private String memberNick;

    /**发消息用户账号*/
    @Field
    private String memberAccount;

    /**发消息用户电话*/
    @Field
    private String memberPhone;

    /**发消息用户备注*/
    @Field
    private String memberRemark;

    /**消息内容*/
    @Field
    private String msg;

    /**发送时间*/
    @Field
    private String createdTime;

    /**消息类型(0,文字、1,图片、2,语言、3,红包、4,视频、5,链接、6,转账、7、地理位置 8、名片 9、自焚消息  10、引用消息  1000,系统消息 1001,撤回消息  */
    @Field
    private Integer  type;

    /**消息类型(0,文字、1,图片、2,语言、3,红包、4,视频、5,链接、6,转账、7、地理位置 8、名片 9、自焚消息  10、引用消息  1000,系统消息 1001,撤回消息  不是我发的+500开始*/
    @Field
    private Integer cellType;

    /**是否是我发送 1.我 2.不是 3.系统消息(撤回消息)*/
    @Field
    private Integer isMe;

    /**已读未读 1.未读 2.已读*/
    @Field
    private List<UniversalKeyValue> isRead;

    /**发送状态 0.发送中 1.发送成功 2.发送失败 3.自焚消息摧毁*/
    @Field
    private Integer status;

    /**红包状态 0.不是红包消息 1.未领取 2.已领取 3.退回*/
    private List<UniversalKeyValue> redEnvelopeStatus;

    /**红包id*/
    @Field
    private String redEnvelopeId;

    /**是否撤回 1.否 2.撤回*/
    @Field
    private Integer withdraw;

    /**登录人主键*/
    @Field
    private String loginMemberId;

    /*接收人集合**/
    private List<String> receiverIds;

    /**删除消息集合*/
    private List<String> deleteMemberId;

    /**数字时间戳*/
    private Long numberTime;




}
