package com.lxd.client;

import com.lxd.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 98050
 * @Time: 2018-10-23 23:48
 * @Feature: 用户feignclient
 */
@FeignClient(value = "every-user")
public interface UserClient {
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username")String username, @RequestParam("password")String password);
}
