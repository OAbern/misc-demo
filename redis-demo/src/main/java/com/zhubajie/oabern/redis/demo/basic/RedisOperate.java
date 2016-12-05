package com.zhubajie.oabern.redis.demo.basic;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * 使用jedis来操纵redis的基础示例
 * @author OABern
 * Created by OABern on 2016/11/29.
 */
public class RedisOperate {

    public static void main(String[] args) {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(2);
//
//        JedisPool pool = new JedisPool();
//        pool.initPool(config);

        Jedis jedis = new Jedis("localhost");
        String key = "demo.list";
        if(jedis.exists(key)) {
            System.out.println("The key【"+key+"】 Has existed!");

        }else {
            System.out.println("The key【"+key+"】 Has not existed! It will be created, and init with 10 item");
            for(int i=0; i<10; i++) {
                jedis.lpush(key, "testV"+i);
            }
        }

        List<String> list = jedis.lrange(key, 0, -1);
        System.out.println("there has 【"+list.size()+"】 items: ");
        System.out.print(" { ");
        for(String s : list) {
            System.out.print(s+", ");
        }
        System.out.print(" } ");
        System.out.println("\n");
    }
}
