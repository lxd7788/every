package com.lxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author lxd
 * @description 启动类
 * @date 2019/12/20
 */
@SpringBootApplication
@EnableEurekaServer
public class EveryRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(EveryRegistryApplication.class, args);
    }
}
