package com.ygb.socketdemo.proto;

import lombok.*;

import java.io.Serializable;

/**
 * 消息服务发送给tcp的包体,tcp再根据改包体解析成Message发给客户端
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023/2/21 18:08
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessagePack implements Serializable {

    /**
     * 用户主键
     */
    private String userId;

    /**
     * 应用id
     */
    private Integer appId;

    /**
     * 接收方
     */
    private String toId;

    /**
     * 客户端标识
     */
    private int clientType;

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 客户端设备唯一标识
     */
    private String imei;

    /**
     * 头像
     */
    private String headImage;

    /**
     *  昵称
     */
    private String nickName;

    /**
     * 指令
     */
    private Integer command;

    /**
     * 业务数据对象，如果是聊天消息则不需要解析直接透传
     */
    private String data;

    /**
     * 时间
     */
    private Long time;
}
