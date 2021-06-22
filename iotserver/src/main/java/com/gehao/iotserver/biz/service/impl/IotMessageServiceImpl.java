package com.gehao.iotserver.biz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import com.gehao.iotserver.biz.service.IotMessageService;
import com.gehao.iotserver.biz.service.RedisService;
import com.gehao.iotserver.dal.dataobject.IotMessageDO;
import com.gehao.iotserver.dal.mapper.IotMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gehao
 * @date 2021/6/16
 */
@Service
public class IotMessageServiceImpl implements IotMessageService {
    @Autowired
    IotMessageMapper iotMessageMapper;

    @Autowired
    RedisService redisService;

    /**
     * 缓存过期时间: 缓存时间设置为1s, 对应前端每1s来获取一次数据
     * 该1s的缓存主要用于解决1s内不同component同时发出的请求
     */
    private Integer expireTime = 1;

    /**
     * 以onlineExpireTime为间隔，如果在这个期间内没有收集到iot的数据更新缓存
     * 那么对应的key就会失效，此时就可以判定为该设备不在线
     */
    private Integer onlineExpireTime = 3;

    /**
     * 总数据的缓存，10s
     */
    private Integer allDataExpireTime = 10;

    public static final String TOTAL_NUM = "totalNum";
    public static final String MESSAGE_NUM_BY_ID = "messageNumById";
    public static final String DEVICE_IDS = "deviceIds";
    public static final String MESSAGES_BY_ID = "messagesById";
    public static final String MESSAGES_BY_ID_AND_NAME = "messagesByIdAndNum";
    public static final String DATA_BY_KEYWORD = "dataByKeyword";

    @Override
    public void insertMessage(IotMessageDO iotMessageDO) {
        redisService.put(iotMessageDO.getClientId(), JSON.toJSONString(iotMessageDO), expireTime);
        iotMessageMapper.insert(iotMessageDO);
    }

    @Override
    public Long getMessageTotalNum() {
        Long result = null;
        if (redisService.exists(TOTAL_NUM)) {
            //System.out.println("getMessageTotalNum use cache!");
            result = JSON.parseObject(redisService.get(TOTAL_NUM), Long.class);
        } else {
            //System.out.println("getMessageTotalNum expire!");
            Long totalNum = iotMessageMapper.getTotalNum();
            redisService.put(TOTAL_NUM, JSON.toJSONString(totalNum), expireTime);
            result = totalNum;
        }
        return result == null ? -1L : result;
    }

    @Override
    public Long getMessageNumById(String id) {
        Long result = null;
        String key = MESSAGE_NUM_BY_ID + id;
        if (redisService.exists(key)) {
            //System.out.println("getMessageNumById use cache!");
            result = JSON.parseObject(redisService.get(key), Long.class);
        } else {
            //System.out.println("messageNumById expire!");
            Long messageNumById = iotMessageMapper.getNumById(id);
            redisService.put(key, JSON.toJSONString(messageNumById), expireTime);
            result = messageNumById;
        }
        return result == null ? -1L : result;
    }

    @Override
    public List<String> getDeviceIds() {
        List<String> result = null;
        if (redisService.exists(DEVICE_IDS)) {
            //System.out.println("getDeviceIds use cache!");
            result = JSON.parseObject(redisService.get(DEVICE_IDS), new TypeReference<List<String>>(){});
        } else {
            //System.out.println("getDeviceIds expire!");
            List<String> deviceIds = iotMessageMapper.getIds();
            redisService.put(DEVICE_IDS, JSON.toJSONString(deviceIds), expireTime);
            result = deviceIds;
        }
        return result == null ? new ArrayList<>() : result;
    }

    @Override
    public List<String> getOnlineDeviceIds() {
        List<String> deviceIds = getDeviceIds();
        List<String> onlineDeviceIds = new ArrayList<>();
        if (deviceIds == null) {
            return onlineDeviceIds;
        }
        // 如果redis还拥有该device id对应的数据，则表示该设备在线
        // 如果redis中找不到该device id对应的数据，说明该设备在expireTime内没有发送过消息，认为它是不在线的
        for(String id : deviceIds) {
            if (redisService.exists(id)) {
                onlineDeviceIds.add(id);
            }
        }
        return onlineDeviceIds;
    }

    @Override
    public List<IotMessageDO> getMessagesById(String id) {
        List<IotMessageDO> result = null;
        String key = MESSAGES_BY_ID + id;
        if (redisService.exists(key)) {
            //System.out.println("getMessagesById use cache!");
            result = JSON.parseObject(redisService.get(key), new TypeReference<List<IotMessageDO>>(){});
        } else {
            //System.out.println("getMessagesById expire!");
            List<IotMessageDO> messagesById = iotMessageMapper.getById(id).stream().sorted((IotMessageDO a, IotMessageDO b)->
                 (int)(a.getTimestamp() - b.getTimestamp())).collect(Collectors.toList());
            redisService.put(key, JSON.toJSONString(messagesById), expireTime);
            result = messagesById;
        }
        return result == null ? new ArrayList<>() : result;
    }

    @Override
    public List<IotMessageDO> getMessagesByIdAndNum(String id, Integer num) {
        List<IotMessageDO> result = null;
        String key = MESSAGES_BY_ID_AND_NAME + id + num;
        if (redisService.exists(key)) {
            //System.out.println("getMessagesByIdAndNum use cache!");
            result = JSON.parseObject(redisService.get(key), new TypeReference<List<IotMessageDO>>(){});
        } else {
            //System.out.println("getMessagesByIdAndNum expire!");
            List<IotMessageDO> messagesByIdAndNum = iotMessageMapper.getByIdLimits(id, num);
            redisService.put(key, JSON.toJSONString(messagesByIdAndNum), expireTime);
            result = messagesByIdAndNum;
        }
        return result == null ? new ArrayList<>() : result;
    }

    @Override
    public Integer getValueById(String id) {
        Integer result = null;
        if (redisService.exists(id)) {
            result = JSON.parseObject(redisService.get(id), IotMessageDO.class).getValue();
        } else {
            // 注意：这里不写缓存，保证缓存中id对应的数据就是最新接收到的iot的数据，而不是从数据库里查询到的数据
            result = iotMessageMapper.getValueById(id);
        }
        return result == null ? -1 : result;
    }

    @Override
    public List<IotMessageDO> getDataByKeyword(String keyword) {
        List<IotMessageDO> result = null;
        keyword = keyword == null ? "_" : keyword;
        String key = DATA_BY_KEYWORD + keyword;
        if (redisService.exists(key)) {
            result = JSON.parseObject(redisService.get(key), new TypeReference<List<IotMessageDO>>(){});
        } else {
            List<IotMessageDO> dataByKeyword = iotMessageMapper.getByKeyword(keyword);
            redisService.put(key, JSON.toJSONString(dataByKeyword), expireTime);
            result = dataByKeyword;
        }
        return result == null ? new ArrayList<>() : result;
    }

}
