package com.sanwu.producer.impl;

import com.sanwu.api.IHelloService;
import com.sanwu.api.VersionConstants;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * <pre>
 * @Description:
 * service
 * </pre>
 *
 * @version v1.0
 * @ClassName: HelloServiceImpl
 * @Author: sanwu
 * @Date: 2020/12/20 14:47
 */
@DubboService(interfaceClass = IHelloService.class,
        version = VersionConstants.VERSION, timeout = 3000 ,
        executes = 1000)
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String name) {
        return name + " consumer~~~~~~";
    }

    @Override
    public String loadBalanceSayHello(String load) {
        return "load balance! consumer " + load;
    }

    @Override
    public String fallbackSayHello(String action) {
        throw new RuntimeException(action);
    }
}
