package com.sanwu.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * <pre>
 * @Description:
 * web 应用
 * </pre>
 *
 * @version v1.0
 * @ClassName: ProducerApplication
 * @Author: sanwu
 * @Date: 2020/12/20 14:05
 */
@EnableDubbo
@SpringBootApplication
@EnableHystrix
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
