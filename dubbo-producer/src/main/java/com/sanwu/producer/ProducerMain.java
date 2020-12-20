package com.sanwu.producer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * <pre>
 * @Description:
 * 服务提供方
 * </pre>
 *
 * @version v1.0
 * @ClassName: ProducerMain
 * @Author: sanwu
 * @Date: 2020/12/20 14:27
 */
@SpringBootApplication
@EnableDubbo
public class ProducerMain {
    public static void main(String[] args) throws InterruptedException {
        new EmbeddedZooKeeper(2181, false).start();

        ConfigurableApplicationContext context = SpringApplication.run(ProducerMain.class, args);

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();

    }
}
