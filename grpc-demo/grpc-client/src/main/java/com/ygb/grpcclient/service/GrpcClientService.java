package com.ygb.grpcclient.service;

import com.ygb.grpclib.HelloReply;
import com.ygb.grpclib.HelloRequest;
import com.ygb.grpclib.SimpleGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * grpc 客户端接口实现
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023-03-12 20:13
 */
@Service
public class GrpcClientService {

    @GrpcClient("local-grpc-server")
    private SimpleGrpc.SimpleBlockingStub simpleStub;

    public String sendMessage(final String name) {
        try {
            final HelloReply response = simpleStub.sayHello(HelloRequest.newBuilder().setData(name).build());
            return response.getData();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }
}
