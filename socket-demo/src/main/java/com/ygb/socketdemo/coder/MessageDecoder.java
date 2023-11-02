package com.ygb.socketdemo.coder;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.message.Message;


import java.util.List;

/**
 * 字节流解码器
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023/2/21 18:02
 */
public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //System.out.println(in);
        //System.out.println(in.readableBytes());
        //
        //byte [] imeiData = new byte[in.readableBytes()];
        //in.readBytes(imeiData);
        //String imei = new String(imeiData);
        //System.out.println(imei);

        if(in.readableBytes() < 4){
            return;
        }

        int dataLen = in.readInt();

        byte [] bodyData = new byte[dataLen];
        in.readBytes(bodyData);
        String s = new String(bodyData);

        ByteBuf buf = Unpooled.copiedBuffer(s, CharsetUtil.UTF_8);
        out.add(buf);




    }
}
