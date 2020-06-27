package lxd.listener;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: 98050
 * @Time: 2018-10-22 19:21
 * @Feature:短信服务监听器
 */
@Component
public class SmsListener {
    private static Logger logger = LoggerFactory.getLogger(SmsListener.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.sms.queue",durable = "true"),
            exchange = @Exchange(value = "leyou.sms.exchange",ignoreDeclarationExceptions = "true"),
            key = {"sms.verify.code"}
    ))
    public void listenSms(Map<String,String> msg){
        if (msg == null || msg.size() <= 0){
            //不做处理
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");

        if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(code)){
            //发送消息
//            try {
//                SendSmsResponse response = this.smsUtils.sendSms(phone, code, smsProperties.getSignName(), smsProperties.getVerifyCodeTemplate());
//            }catch (ClientException e){
//                return;
//            }
            logger.info("手机是==》"+phone+"验证码是======》"+code);
        }else {
            //不做处理
            return;
        }
    }
}
