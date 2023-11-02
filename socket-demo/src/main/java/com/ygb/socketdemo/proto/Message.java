package com.ygb.socketdemo.proto;


import lombok.*;


/**
 * 私有协议内部封装
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023/2/21 18:05
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {

    /**消息头*/
    private MessageHeader messageHeader;

    /**消息体*/
    private Object messagePack;
}
