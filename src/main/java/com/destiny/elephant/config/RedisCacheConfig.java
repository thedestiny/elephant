package com.destiny.elephant.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;

/**
 * Created by wangl on 2017/11/25.
 * todo:
 */
@EnableCaching
@Configuration
public class RedisCacheConfig {

    private Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

    @Autowired
    private RedisProperties redisProperties;


  /*
  @Bean
  public JedisPool jedisPool() {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        config.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxActive());
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        *//*  GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, boolean ssl*//*
        JedisPool jedisPool = new JedisPool(config, redisProperties.getHost(), redisProperties.getPort(), redisProperties.getTimeout(), redisProperties.getPassword(), redisProperties.getDatabase(), false);

        Jedis jedis = jedisPool.getResource();
        String info = jedis.info();

        logger.info("redis info is {}", info);

        return jedisPool;
    }*/


    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);

        logger.info(" >>>  properties host {}, port {}, database {}, password {}", redisProperties.getHost(), redisProperties.getPort(), redisProperties.getDatabase(), redisProperties.getPassword());

//        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        //这里如果启用fastjson序列化对象到redis的话 启动必须加参数 -Dfastjson.parser.autoTypeSupport=true
//        RedisSerializer fastJson = fastJson2JsonRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

   /* @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }*/

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1));
        // 设置缓存有效期一小时
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory)).cacheDefaults(redisCacheConfiguration).build();
    }


}
