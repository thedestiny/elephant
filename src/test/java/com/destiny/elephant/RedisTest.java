package com.destiny.elephant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class RedisTest {


    public static void main(String[] args) {


        System.out.println("ss");

        JedisPoolConfig pool = new JedisPoolConfig();
        pool.setTestOnReturn(true);
        pool.setMaxWaitMillis(10000);
        pool.setMaxIdle(3);
        pool.setMaxWaitMillis(10000);
        pool.setMaxTotal(200);

        //声明一个set 存放哨兵集群的地址和端口
        Set<String> sentinels = new HashSet<String>();
        sentinels.add("192.168.101.79:26379");
        sentinels.add("192.168.101.79:26380");
        sentinels.add("192.168.101.79:26381");
        // 名称 sentinel pool timeout auth
        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels, pool,2000,"foobared");
        // 使用sentinelPool获取jedis对象
        Jedis jedis = sentinelPool.getResource();
        jedis.set("dddddd","fffff");
        System.out.println(jedis.get("dddddd"));
    }


}
