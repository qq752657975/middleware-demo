package com.ygb.dubboproduction.provider;

import com.ygb.duibbointerface.DemoService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.util.concurrent.TimeUnit;

/**
 * dubbo服务接口实现
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2022/7/30 19:12
 */
@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("执行了服务" + name);
        URL url = RpcContext.getContext().getUrl();
        return  String.format("%s：%s, Hello, %s", url.getProtocol(), url.getPort(), name);  // 正常访问;
    }
}
