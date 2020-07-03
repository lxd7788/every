package lxd.MqDemo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class ProduceConfirms implements RabbitTemplate.ConfirmCallback , RabbitTemplate.ReturnCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void init() {
//        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 发送
     */
    public void sendMq(String exchange, String msg){
//        rabbitTemplate.setConfirmCallback(this);//也可以放到这
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("callbackSender UUID: " + correlationData.getId());
        rabbitTemplate.convertAndSend(exchange, "key", msg, correlationData);
    }

    /**
     * 消息发送交换机确认
     * @param correlationData
     * @param b
     * @param s
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (b) {
            System.out.println("消息确认成功, id:{}"+ id);
        } else {
            System.out.println("消息未成功投递, id:{}, cause:{}"+id+s);
        }

    }

    /**
     * 消息没有路由到队列
     * @param message
     * @param i
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("消息主体 message : "+message);
        System.out.println("消息主体 message : "+i);
        System.out.println("描述："+s);
        System.out.println("消息使用的交换器 exchange : "+s1);
        System.out.println("消息使用的路由键 routing : "+s2);

    }
}
