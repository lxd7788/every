package com.lxd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @ClassName DemoTest
 * @Author lxd
 * @Date 2020/6/6 22:38
 * @Description TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = H5DemoServiceApplication.class) //主类参数不加也行，加与不加差异如何未知
public class DemoAdd {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

//    @Autowired
//    private RedisTemplate redis;

    /**
     * 商品redis
     * key："kill" + 商品id
     *
     * value
     */
    @Test
    public void add(){

        redisTemplate.opsForValue().set("kill"+"1",10);


    }

    @Test
    public void del(){
//        Boolean kk = redisTemplate.hasKey("kill1");
//
//        System.out.println(kk);
        Long n = redisTemplate.opsForValue().increment("kill" + 1, -1);


    }

    @Test
    public void get(){
        final String key=new StringBuffer().append("test").append("001").append("-RedisLock").toString();
        final String value= UUID.randomUUID().toString();
        System.out.println(key+"--->"+value);
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, value);
        System.out.println(aBoolean);

    }
}
