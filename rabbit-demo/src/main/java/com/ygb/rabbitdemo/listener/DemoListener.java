package com.ygb.rabbitdemo.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * TODO
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2022/7/18 13:46
 */
@Slf4j
@Component
public class DemoListener {

    @RabbitListener(queues = "redBagExpiredQueue.delayed", ackMode = "MANUAL")
    public void broadcastMeetingAlarm(Message message, Channel channel) throws Exception {
        String redBagId = new String(message.getBody(), message.getMessageProperties().getContentEncoding());
        log.info("消息来了:{}",redBagId);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
