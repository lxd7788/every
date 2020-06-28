package com.lxd.controller;


import com.lxd.properties.JwtProperties;
import com.lxd.service.AuthService;
import com.lxd.utils.CookieUtils;
import com.lxd.utils.JwtUtils;
import com.lxd.utils.UserInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 98050
 * @Time: 2018-10-23 22:43
 * @Feature: 登录授权
 */
@Controller
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties properties;

    /**
     * 登录授权
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accredit")
    @ApiOperation(value = "登录",notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名" ,required = true),
            @ApiImplicitParam(name="username",value = "密码",required = true)
    })
    public ResponseEntity<Void> authentication(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        logger.info("用户名："+username+"密码:"+password);
        //1.登录校验
        String token = this.authService.authentication(username,password);
        if (StringUtils.isBlank(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //2.将token写入cookie，并指定httpOnly为true，防止通过js获取和修改
        CookieUtils.setCookie(request,response,properties.getCookieName(),token,properties.getCookieMaxAge(),true);

        return ResponseEntity.ok().build();
    }

    //测试哦
    @PostMapping("test")
    public String tt(@RequestParam("username") String username, @RequestParam("password") String password){
        String token = this.authService.authentication(username,password);
        System.out.println(token);
        return token;
    }
    /**
     * 用户验证
     * @param token
     * @return
     */
    @GetMapping("verify")
    @ApiOperation(value = "状态",notes = "用户状态")
    public ResponseEntity<UserInfo> verifyUser(@CookieValue("COOKNA") String token,HttpServletRequest request,
                                               HttpServletResponse response){
        try{
            //1.从token中解析token信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(token,this.properties.getPublicKey());
            //2.解析成功要重新刷新token
            token = JwtUtils.generateToken(userInfo,this.properties.getPrivateKey(),this.properties.getExpire());
            //3.更新Cookie中的token
            CookieUtils.setCookie(request,response,this.properties.getCookieName(),token,this.properties.getCookieMaxAge()*200);
            //4.解析成功返回用户信息
            return ResponseEntity.ok(userInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        //5.出现异常,相应401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
