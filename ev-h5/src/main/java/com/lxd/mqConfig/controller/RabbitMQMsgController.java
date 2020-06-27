package com.lxd.mqConfig.controller;

import com.lxd.mqConfig.send.DelayMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQMsgController {

    @Autowired
    private DelayMessageSender sender;

    @RequestMapping("/sendmsg")
    public void sendMsg(String msg, Integer delayType){

//        System.out.println("当前时间：{},死信队列A收到消息：{}"+ new Date().toString()+ msg);
        sender.sendMsg(msg, delayType);
    }
}
