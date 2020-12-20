package com.sanwu.consumer.action;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sanwu.api.IHelloService;
import com.sanwu.api.VersionConstants;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * @Description:
 * 消费动作， 消费提供Hello服务的服务端
 * </pre>
 *
 * @version v1.0
 * @ClassName: HelloService
 * @Author: sanwu
 * @Date: 2020/12/20 14:13
 */

@Component
public class HelloAction{


    @DubboReference(interfaceClass = IHelloService.class, timeout = 1000,
            version = VersionConstants.VERSION ,loadbalance = CommonConstants.DEFAULT_LOADBALANCE)
    private IHelloService helloService;


    public String doSayHello(String name) {
        try {
            return helloService.sayHello(name);
        } catch (Exception e) {
            e.printStackTrace();
            return "Throw Exception";
        }
    }

    @LoadBalanced
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")})
    public String loadBalanceSayHello(String action){
        try {
            return helloService.loadBalanceSayHello(action);
        } catch (Exception e) {
            e.printStackTrace();
            return "Throw Exception";
        }
    }

    @LoadBalanced
    @HystrixCommand(fallbackMethod = "reliable")
    public String fallbackSayHello(String action) {
        return helloService.fallbackSayHello(action);
    }

    public String reliable(String name) {
        return "hystrix fallback value" +name;
    }
}
