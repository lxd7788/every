package com.lxd.service.impl;

import com.lxd.mapper.ItemKillMapper;
import com.lxd.mqConfig.config.RabbitMQConfig;
import com.lxd.pojo.ItemKill;
import com.lxd.pojo.ItemKillSuccess;
import com.lxd.service.ItemKillSuccessService;
import com.lxd.service.KillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName KillServiceImpl
 * @Author lxd
 * @Date 2020/6/9 21:02
 * @Description TODO
 */
@Service
public class KillServiceImpl implements KillService {

    private static Logger logger = LoggerFactory.getLogger(KillServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ItemKillMapper itemKillMapper;
    @Autowired
    private ItemKillSuccessService itemKillSuccessService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public Boolean killItem(Integer killId,long id) {
        Boolean result=false;
        //开始下单逻辑
        final String key=new StringBuffer().append(killId).append(id).append("-RedisLock").toString();
        final String value=UUID.randomUUID().toString();

        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, value);
        if(aBoolean){
            redisTemplate.expire(key,30, TimeUnit.SECONDS);

            try {
                //订单查询该用户是否已经买过，yes-》false0
                Boolean uu = redisTemplate.hasKey("user" + id+killId);
                if(uu){
                    logger.info("用户已经下单");
                    return result;
                }
                //从redis查询商品是否可以秒杀，根据数量>0表示可以秒杀
                Boolean kk = redisTemplate.hasKey("kill" + killId);
                if (!kk){
                    logger.info("秒杀商品不存在");
                    return result;
                }
                //存在时判断剩余量
                int num = (int)redisTemplate.opsForValue().get("kill" + killId);
                if(num<=0){
                    logger.info("秒杀商品卖完");
                    return result;
                }

                //减库存
                Long n = redisTemplate.opsForValue().increment("kill" + killId, -1);
                if(n>0){
                    //扣减成功
                    ItemKill itemKill1 = itemKillMapper.selectByPrimaryKey(killId);
                    commonRecordKillSuccessInfo(itemKill1,id);
                    result = true;
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(value.equals(redisTemplate.opsForValue().get(key))){
                    redisTemplate.delete(key);
                };
            }

        }
        return result;
//        if (itemKillSuccessService.countByKillUserId(killId,id) <= 0){
//            //分布式锁
//            final String key=new StringBuffer().append(killId).append(id).append("-RedisLock").toString();
//
//            final String value=UUID.randomUUID().toString();
//
//            System.out.println(key+"------"+value);
//            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, value);
//            if(aBoolean){
//                redisTemplate.expire(key,30, TimeUnit.SECONDS);
//
//                try {
//                    ItemKill  itemKill = itemKillMapper.selectByPrimaryKey(killId);
//                    //判断该商品是否可以秒杀
//                    if (itemKill !=null && 1 == itemKill.getIsActive()){
//                        //库存-1，在库存大于1的情况下
//                        int i = itemKillMapper.updateKillItem(killId);
//                        //>0减库存成功，通知订单入库，mq发送成信息
//                        if(i>0){
//                            //订单
//                            ItemKill itemKill1 = itemKillMapper.selectByPrimaryKey(killId);
//                            commonRecordKillSuccessInfo(itemKill1,id);
//                            result = true;
//                            return result;
//                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if(value.equals(redisTemplate.opsForValue().get(key))){
//                        redisTemplate.delete(key);
//                    };
//                }
//
//            }
//
//        }else {
//            System.out.println("您已经抢购过该商品了!");
//        }

    }

    /**
     * 创建订单
     * @param kill
     */
    private void commonRecordKillSuccessInfo(ItemKill kill,long id){
        ItemKillSuccess entity=new ItemKillSuccess();

        entity.setCode(UUID.randomUUID().toString());//订单
        entity.setItemId(kill.getItemId());
        entity.setKillId(kill.getId());
        System.out.println("------------"+id);
        entity.setUserId(id);
        entity.setStatus(0);
        entity.setCreateTime(new Date());
        entity.setJiage(kill.getJiage());

        int i = itemKillSuccessService.insertKill(entity);
        if(1>0){
            System.out.println("=========>成功====>发送私信队列订单编码"+entity.getCode());
            //成功发送mq
            //添加私信队列处理超时的订单
            rabbitTemplate.convertAndSend(RabbitMQConfig.DELAY_EXCHANGE_NAME, RabbitMQConfig.DELAY_QUEUEB_ROUTING_KEY, entity.getCode());

        }
    }
}
