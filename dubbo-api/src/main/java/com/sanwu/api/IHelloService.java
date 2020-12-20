package com.sanwu.api;

/**
 * <pre>
 * @Description:
 * TODO
 * </pre>
 *
 * @version v1.0
 * @ClassName: IHelloService
 * @Author: sanwu
 * @Date: 2020/12/20 13:56
 */
public interface IHelloService {

    String sayHello(String name);

    String loadBalanceSayHello(String load);

    String fallbackSayHello(String action);
}
