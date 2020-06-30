package com.lxd.mqConfig.consumer;

import com.lxd.mqConfig.config.RabbitMQConfig;
import com.lxd.service.ItemKillSuccessService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class DeadLetterQueueConsumer {
    @Autowired
    private ItemKillSuccessService itemKillSuccessService;

    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUEA_NAME) //死信队列A
    public void receiveA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        System.out.println("当前时间："+ new Date().toString()+"死信队列A收到消息：{}"+ msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

//    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUEB_NAME) //死信队列B
//    public void receiveB(Message message, Channel channel) throws IOException {
//        String msg = new String(message.getBody());
////        log.info("当前时间：{},死信队列B收到消息：{}", new Date().toString(), msg);
//        System.out.println("当前时间："+ new Date().toString()+"死信队列B收到消息：{}"+ msg);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    }

    /**
     *
     * @param message
     * @param channel
     * @throws
     *
     */
//    订单过期处理
    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUEB_NAME) //死信队列B
    public void receiveB(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
//        log.info("当前时间：{},死信队列B收到消息：{}", new Date().toString(), msg);
        System.out.println("当前时间："+ new Date().toString()+"死信队列B收到消息：{}"+ msg);


        int i = itemKillSuccessService.yodateOrderStatus(msg);
        System.out.println("结果"+i);
        //如果订单依然没有支付，修改订单状态，并库存+1
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
