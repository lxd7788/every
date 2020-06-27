package com.lxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
* @author lxd
* @description
* @date 2019/12/23
*/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.lxd.mapper")
public class EveryAuthServiceApplication {
    public static void main(String[] args) {

        SpringApplication.run(EveryAuthServiceApplication.class, args);
    }
}
