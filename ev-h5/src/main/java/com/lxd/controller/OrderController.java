package com.lxd.controller;

import com.lxd.pojo.ItemKillSuccess;
import com.lxd.properties.JwtProperties;
import com.lxd.service.ItemKillSuccessService;
import com.lxd.utils.JwtUtils;
import com.lxd.utils.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName OrderController
 * @Author lxd
 * @Date 2020/6/20 20:52
 * @Description TODO
 */
@RestController
@RequestMapping("/pay")
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private ItemKillSuccessService itemKillSuccessService;

    @Autowired
    private JwtProperties properties;

    @PostMapping("/order")
    public ItemKillSuccess getItem(String code){
        logger.info("============="+code);
        ItemKillSuccess itemKillSuccess = itemKillSuccessService.selectByCode(code);
        logger.info(itemKillSuccess.getCode());
        return itemKillSuccess;
    }

    @PostMapping("/zhifu")
    public ResponseEntity<Object> zhifu(String id){
        ItemKillSuccess bean = new ItemKillSuccess();
        bean.setCode(id);
        bean.setStatus(1);
        int i = itemKillSuccessService.updateStatus(bean);
        if(1>0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * 订单list
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Object>  all(@CookieValue("COOKNA") String token, HttpServletRequest request,
                                       HttpServletResponse response){
        //1.从token中解析token信息
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, this.properties.getPublicKey());
            List<ItemKillSuccess> list = itemKillSuccessService.selectList(userInfo.getId());
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //5.出现异常,相应401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
