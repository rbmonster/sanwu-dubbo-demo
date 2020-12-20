package com.sanwu.consumer.controller;

import com.sanwu.consumer.action.HelloAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * @Description:
 * 外部http接口
 * </pre>
 *
 * @version v1.0
 * @ClassName: UserController
 * @Author: sanwu
 * @Date: 2020/12/20 14:13
 */
@RestController
public class UserController {

    private HelloAction helloAction;

    @Autowired
    public UserController(HelloAction helloAction) {
        this.helloAction = helloAction;
    }

    @GetMapping("/sayHello")
    public String sayHello(String username) {
        return helloAction.doSayHello(username);
    }

    @GetMapping("/loadBalance")
    public String loadBalance(String action) {
        return helloAction.loadBalanceSayHello(action);
    }


    @GetMapping("/fallback")
    public String fallback(String action) {
        return helloAction.fallbackSayHello(action);
    }
}
