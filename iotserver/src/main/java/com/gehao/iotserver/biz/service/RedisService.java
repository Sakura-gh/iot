package com.gehao.iotserver.biz.service;

/**
 * @author gehao
 */
public interface RedisService {
    /**
     * 缓存key是否存在
     * @param key
     * @return
     */
    Boolean exists(String key);

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 写入缓存, 不设置expireTime
     * @param key
     * @param value
     * @return
     */
    Boolean put(String key, String value);

    /**
     * 写入缓存, 设置expireTime
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    Boolean put(String key, String value, Integer expireTime);

    /**
     * 移出key对应的缓存
     * @param key
     * @return
     */
    Boolean remove(String key);
}
