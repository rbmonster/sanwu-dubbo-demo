# dubbo demo
启动顺序
1. 启动producer
2. 启动consumer
> 1. 使用内置的ZooKeeper无需额外启动 
> 2. producer使用spring boot start，使用的是AnnotationContext，通过CountDownLunch阻塞。
> 3. consumer 为常见的一个web 应用，使用controller 接受外部请求，内部使用dubbo与服务提供方进行消费



## 基础架构
基础的架构启动流程图
![avatar](https://github.com/rbmonster/learning-note/blob/master/src/main/java/com/design/picture/dubboStructure.jpg)
- Provider： 暴露服务的服务提供方
- Consumer： 调用远程服务的服务消费方
- Registry： 服务注册与发现的注册中心
- Monitor： 统计服务的调用次数和调用时间的监控中心
- Container： 服务运行容器

流程：
1. 服务提供者启动spring 容器，注册自己提供的服务到注册中心。
2. 服务消费者启动spring 容器，向注册中心订阅自己所需的服务。
3. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
4. 服务消费者，从提供者地址列表中，基于负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
5. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。


### 项目架构
基础的项目架构可以分为三层api、producer、consumer。
1. api 用于提供服务的相关接口，以jar包的形式引入到producer与consumer中。
2. producer实现api的接口方法，使用注解@DubboService标记接口为一个dubbo服务。
3. consumer使用注解@DubboReference注入需要订阅服务的api接口。

## 基本注解

```
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-spring-boot-starter</artifactId>
    <version>2.7.8</version>
</dependency>

<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo</artifactId>
</dependency>
```

### @EnableDubbo
开启dubbo的自动配置扫描相关注解。

### @DubboReference
标注接口需要从注册中心订阅服务，可以标记负载均衡的方式。

```
    @DubboReference(interfaceClass = IHelloService.class, timeout = 1000,
            version = VersionConstants.VERSION ,
            loadbalance = CommonConstants.DEFAULT_LOADBALANCE) // 负载均衡
```
### @DubboService
标注一个接口实现类提供服务，并注册到注册中心， 可以设置服务限流。

```
@DubboService(interfaceClass = IHelloService.class,
        version = VersionConstants.VERSION, 
        timeout = 3000 ,  // 超时中断
        executes = 1000)  // 限流
```

## 负载均衡方法
1. random 随机
2. RoundRobin 基于权重的轮询负载均衡机制
3. 一致性hash
4. 最少活跃调用数均衡算法。最少活跃调用数，相同活跃数的随机，活跃数指调用前后计数差。

## 服务降级
Dubbo的架构支持hystrix 的相关注解

