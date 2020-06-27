package com.lxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lxd.mapper")
public class EveryUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(EveryUserApplication.class, args);
    }
}
