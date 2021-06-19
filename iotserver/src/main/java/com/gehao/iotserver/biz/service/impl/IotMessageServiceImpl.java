package com.gehao.iotserver.biz.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;

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

    @Override
    public void insertMessage(IotMessageDO iotMessageDO) {
        iotMessageMapper.insert(iotMessageDO);
    }

    @Override
    public Long getMessageTotalNum() {
        return iotMessageMapper.getTotalNum();
    }

    @Override
    public List<String> getDeviceIds() {
        return iotMessageMapper.getIds();
    }

    @Override
    public List<String> getOnlineDeviceIds() {
        return null;
    }

    @Override
    public List<IotMessageDO> getMessagesById(String id) {
        return iotMessageMapper.getById(id).stream().sorted((IotMessageDO a, IotMessageDO b)->{
            return (int)(a.getTimestamp() - b.getTimestamp());
        }).collect(Collectors.toList());
    }

    @Override
    public List<IotMessageDO> getMessagesByIdAndNum(String id, Integer num) {
        return iotMessageMapper.getByIdLimits(id, num);
    }

    @Override
    public List<Map<String, IotMessageDO>> getLatestMessages() {
        return null;
    }

}
