package com.ygb.socketdemo.proto;

import lombok.*;

/**
 * 私有协议定义封装
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023/2/21 18:03
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageHeader {

    //消息操作指令 十六进制 一个消息的开始通常以0x开头
    //4字节
    private Integer command;
    //4字节 版本号
    private Integer version;
    //4字节 端类型
    private Integer clientType;
    //4字节 应用ID
    private Integer appId;
    //4字节 解析类型 数据解析类型 和具体业务无关，后续根据解析类型解析data数据 0x0:Json,0x1:ProtoBuf,0x2:Xml,默认:0x0
    private Integer messageType = 0x0;
    //imei号
    private String imei;
    //userId
    private String userId;
}
