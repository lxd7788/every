package com.lxd.service.impl;


import com.lxd.client.UserClient;
import com.lxd.pojo.User;
import com.lxd.properties.JwtProperties;
import com.lxd.service.AuthService;
import com.lxd.utils.JwtUtils;
import com.lxd.utils.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @Author: 98050
 * @Time: 2018-10-23 22:47
 * @Feature:
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties properties;

    /**
     * 用户授权
     * @param username
     * @param password
     * @return
     */
    @Override
    public String authentication(String username, String password) {

        try{
            //1.调用微服务查询用户信息
            ResponseEntity<User> users = this.userClient.queryUser(username,password);
            users.getBody();
            //2.查询结果为空，则直接返回null
            User user=users.getBody();
            if (user == null){
                return null;
            }
            //3.查询结果不为空，则生成
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()),
                    properties.getPrivateKey(), properties.getExpire());
            return token;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
