package com.lxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
* @author lxd
* @description
* @date 2019/12/20 
*/

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class EveryGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(EveryGatewayApplication.class, args);
    }
}
