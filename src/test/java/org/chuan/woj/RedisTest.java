package org.chuan.woj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * 测试类通用模板
 *
 * @Author: chuan-wxy
 * @Date: 2024/8/16 9:14
 * @Description:
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    //密钥类型操作
    @Test
    void KeyTypeOperationsTest() {
        //string
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","KeyType");
        System.out.println(valueOperations.get("name"));
    }

    //键绑定操作
    @Test
    void KeyBoundOperationsTest() {
        //string
        redisTemplate.boundValueOps("name").set("KeyBound");
        System.out.println(redisTemplate.boundValueOps("name").get());

        BoundValueOperations username = redisTemplate.boundValueOps("username");
        //10秒过期
        username.set("1000",10, TimeUnit.SECONDS);


        BoundHashOperations car = redisTemplate.boundHashOps("car");
        car.put("p_id","1");
        //不存在则删除
        Boolean i = car.putIfAbsent("p_total","{asd,asd,asd}");
        System.out.println(i);
        Boolean x = car.putIfAbsent("p_total","{asd,asd,asd}");
        System.out.println(x);
        //获取所有的值
        System.out.println(car.entries());
        //获取单个值
        System.out.println(car.get("p_id"));
        //删除某一项
        car.delete("p_id");
        //删除整个car
        redisTemplate.delete("car");


    }
}
