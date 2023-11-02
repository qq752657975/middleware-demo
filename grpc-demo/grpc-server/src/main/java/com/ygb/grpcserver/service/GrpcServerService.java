package com.ygb.grpcserver.service;

import com.ygb.grpclib.HelloReply;
import com.ygb.grpclib.HelloRequest;
import com.ygb.grpclib.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Date;

/**
 * grpc 接口实现
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023-03-12 20:10
 */
@GrpcService
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {


    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("你好， " + request.getName() + ", " + new Date()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
