package com.lxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName UploadApplication
 * @Author lxd
 * @Date 2020/7/6 21:43
 * @Description TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.lxd.mapper")
public class UploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(UploadApplication.class, args);
    }
}
