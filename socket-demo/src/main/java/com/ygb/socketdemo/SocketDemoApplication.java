package com.ygb.socketdemo;


import cn.hutool.json.JSONUtil;
import com.ygb.socketdemo.coder.MessageDecoder;
import com.ygb.socketdemo.handler.NettyClientHandler;
import com.ygb.socketdemo.proto.Message;
import com.ygb.socketdemo.proto.MessageHeader;
import com.ygb.socketdemo.proto.MyMessageProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@SpringBootApplication
public class SocketDemoApplication {

    public static Channel channel;

    public static void main(String[] args) throws InterruptedException {
        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            //注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap.group(group) //设置线程组
                    .channel(NioSocketChannel.class) // 使用 NioSocketChannel 作为客户端的通道实现
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            //加入处理器
                            channel.pipeline().addLast(new MessageDecoder());
                            channel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("netty client start");
            //启动客户端去连接服务器端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 19002).sync();



            channel = channelFuture.channel();
            Message message = new Message();
            MessageHeader messageHeader = new MessageHeader();
            messageHeader.setClientType(1);
            messageHeader.setCommand(0x2328);
            messageHeader.setAppId(1);
            messageHeader.setImei(UUID.randomUUID().toString());
            messageHeader.setVersion(1);
            messageHeader.setUserId("1622505236319694851");
            message.setMessageHeader(messageHeader);
            //message.setMessagePack("1");

            String s = JSONUtil.toJsonStr(message);
            int length = s.getBytes(StandardCharsets.UTF_8).length;
            byte[] array =
                    ByteBuffer.allocate(4 + length)
                            .putInt(s.getBytes(StandardCharsets.UTF_8).length)
                            .put(s.getBytes(StandardCharsets.UTF_8))
                            .array();
            ByteBuf buf = Unpooled.copiedBuffer(array);
            channel.writeAndFlush(buf);
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }


        //SpringApplication.run(SocketDemoApplication.class, args);
    }

}
