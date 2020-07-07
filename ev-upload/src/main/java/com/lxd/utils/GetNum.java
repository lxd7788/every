package com.lxd.utils;

import java.util.Random;

/**
 * @ClassName GetNum
 * @Author lxd
 * @Date 2020/7/7 22:31
 * @Description TODO
 */
public  class GetNum {

    public static int num(){
//        String s = UUID.randomUUID().toString();
//        String n=s.substring(0,8);

//        long l = System.currentTimeMillis();
//        int n=(int) l/1000;
        String val = "";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return Integer.parseInt(val);

    }


}
