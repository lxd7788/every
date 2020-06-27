package com.lxd.controller;


import com.lxd.client.UserClient;
import com.lxd.pojo.ItemKillSuccess;
import com.lxd.properties.JwtProperties;
import com.lxd.service.KillService;
import com.lxd.service.impl.ItemKillSuccessServiceiImpl;
import com.lxd.utils.JwtUtils;
import com.lxd.utils.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName KillController
 * @Author lxd
 * @Date 2020/6/9 20:55
 * @Description 秒杀
 */
@RestController
@RequestMapping("/sha")
public class KillController {
    
    @Autowired
    private KillService killService;

    @Autowired
    private JwtProperties properties;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ItemKillSuccessServiceiImpl itemKillSuccessServiceiImpl;

    //    @PostMapping("/xiadan")
//    public ResponseEntity<Object> sha(String id){
//        //参数校验
//        //用户登录？是不是应该在网关就拦截
//        //下单
//        Boolean state = killService.killItem(Integer.parseInt(id));
//        if(!state){
//           return ResponseEntity.ok("商品已抢购完毕或者不在抢购时间段哦!");
//        }
//        return ResponseEntity.ok("抢购成功");
//    }
    @PostMapping("/xiadan")
    public ResponseEntity<Object> sha(String id, @CookieValue("COOKNA") String token, HttpServletRequest request,
                                      HttpServletResponse response){
        //参数校验
        //用户登录？
        UserInfo userInfo = null;
        try{
            //1.从token中解析token信息
            userInfo = JwtUtils.getInfoFromToken(token,this.properties.getPublicKey());
            //刷新cookie的token时间
            //下单
            System.out.println("id---------》"+userInfo.getId());
            Boolean state = killService.killItem(Integer.parseInt(id),userInfo.getId());
            if(!state){
//                return ResponseEntity.ok("商品已抢购完毕或者不在抢购时间段哦!");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
//            return ResponseEntity.ok("抢购成功");
            //获取订单code返回
            ItemKillSuccess itemKillSuccess = new ItemKillSuccess();
            itemKillSuccess.setKillId(Integer.parseInt(id));
            itemKillSuccess.setUserId(userInfo.getId());
            ItemKillSuccess order = itemKillSuccessServiceiImpl.selectItemOrder(itemKillSuccess);
            return new ResponseEntity<>(order.getCode(), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        //5.出现异常,相应401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
