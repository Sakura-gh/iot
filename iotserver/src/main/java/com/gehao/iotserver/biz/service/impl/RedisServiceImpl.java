package com.gehao.iotserver.biz.service.impl;

import com.gehao.iotserver.biz.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;

/**
 * @author gehao
 * @date 2021/6/16
 */
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Boolean exists(String key) {
        try {
            // cache key md5加密，避免key长度过大
            key = DigestUtils.md5DigestAsHex(key.getBytes());
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            System.out.println("redis judge exists error!");
            return false;
        }
    }

    @Override
    public String get(String key) {
        try {
            // cache key md5加密，避免key长度过大
            key = DigestUtils.md5DigestAsHex(key.getBytes());
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            System.out.println("redis get error!");
            return null;
        }
    }

    @Override
    public Boolean put(String key, String value) {
        try {
            // cache key md5加密，避免key长度过大
            key = DigestUtils.md5DigestAsHex(key.getBytes());
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            System.out.println("redis put error!");
            return false;
        }
    }

    @Override
    public Boolean put(String key, String value, Integer expireTime) {
        try {
            // cache key md5加密，避免key长度过大
            key = DigestUtils.md5DigestAsHex(key.getBytes());
            redisTemplate.opsForValue().set(key, value, expireTime);
            return true;
        } catch (Exception e) {
            System.out.println("redis put error!");
            return false;
        }
    }

    @Override
    public Boolean remove(String key) {
        try {
            // cache key md5加密，避免key长度过大
            key = DigestUtils.md5DigestAsHex(key.getBytes());
            if (exists(key)) {
                redisTemplate.delete(key);
            }
            return true;
        } catch (Exception e) {
            System.out.println("redis remove error!");
            return false;
        }
    }
}
