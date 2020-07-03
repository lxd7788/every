package com.lxd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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
    private RedisTemplate redisTemplate;

    /**
     * 商品redis
     * key："kill" + 商品id
     * value
     */
    @Test
    public void add(){

        redisTemplate.opsForValue().set("kill"+"秒傻商品id",10);

    }

    @Test
    public void t1(){
        System.out.println(1111);
    }
}
