package com.lxd.controller;

import com.lxd.pojo.User;
import com.lxd.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


/**
 * @Author: 98050
 * @Time: 2018-10-21 18:40
 * @Feature:
 */
@Controller
@Api("用户相关api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户数据检查
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data,@PathVariable(value = "type") Integer type){
        Boolean result = this.userService.checkData(data,type);
        if (result == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @PostMapping("code")
    @ApiOperation(value = "短信api",notes = "向手机号发送验证码")
    @ApiImplicitParam(name = "phone",value = "手机号",required = true)
    public ResponseEntity senVerifyCode(@RequestParam("phone") String phone){
        Boolean result = this.userService.sendVerifyCode(phone);
        if (result == null || !result){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 注册
     * @param user
     * @param code
     * @return
     */
    @PostMapping("register")
    @ApiOperation(value = "注册",notes = "用户信息注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户信息" ,required = true),
            @ApiImplicitParam(name="code",value = "验证码",required = true)
    })
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code){
        Boolean result = this.userService.register(user,code);
        if(result == null || !result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 用户验证
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username")String username, @RequestParam("password")String password){
        System.out.println(username+"------>"+password);
        User user = this.userService.queryUser(username,password);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }

}
