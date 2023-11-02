package com.ygb.socketdemo.handler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.ygb.socketdemo.proto.Message;
import com.ygb.socketdemo.proto.MessageHeader;
import com.ygb.socketdemo.proto.MessagePack;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import java.util.UUID;

/**
 * TODO
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023/2/21 19:51
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    /**
     * 当客户端连接服务器完成就会触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    //当通道有读取事件时会触发，即服务端发送数据给客户端
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String s = buf.toString(CharsetUtil.UTF_8);
        MessagePack messagePack = JSONUtil.toBean(s, MessagePack.class);
        System.out.println("收到服务端的消息:" + s);
        System.out.println("服务端的地址： " + ctx.channel().remoteAddress());

        if(BeanUtil.isNotEmpty(messagePack) && messagePack.getCommand().equals(0x232a)){
            log.info("其他端登录,发送下线消息");
            Message message = new Message();
            MessageHeader messageHeader = new MessageHeader();
            messageHeader.setClientType(1);
            messageHeader.setCommand(0x232b);
            messageHeader.setAppId(1);
            messageHeader.setImei(UUID.randomUUID().toString());
            messageHeader.setVersion(1);
            messageHeader.setUserId("1622505236319694851");
            message.setMessageHeader(messageHeader);

            String s1 = JSONUtil.toJsonStr(message);
            int length = s1.getBytes(StandardCharsets.UTF_8).length;
            byte[] array =
                    ByteBuffer.allocate(4 + length)
                            .putInt(s1.getBytes(StandardCharsets.UTF_8).length)
                            .put(s1.getBytes(StandardCharsets.UTF_8))
                            .array();
            ByteBuf buf1 = Unpooled.copiedBuffer(array);
            ctx.writeAndFlush(buf1);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
