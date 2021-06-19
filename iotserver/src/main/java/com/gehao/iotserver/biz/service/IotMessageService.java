package com.gehao.iotserver.biz.service;

import java.util.List;
import java.util.Map;

import com.gehao.iotserver.dal.dataobject.IotMessageDO;

/**
 * @author gehao
 */
public interface IotMessageService {
    /**
     * 插入一条新的iot数据
     * @param iotMessageDO
     */
    void insertMessage(IotMessageDO iotMessageDO);

    /**
     * 获取iot数据的总数
     * @return
     */
    Long getMessageTotalNum();

    /**
     * 获取当前数据库中所有设备的device id
     * @return
     */
    List<String> getDeviceIds();

    /**
     * 获取当前在线的设备id
     * @return
     */
    List<String> getOnlineDeviceIds();

    /**
     * 获取该id设备的所有信息记录
     * @param id
     * @return
     */
    List<IotMessageDO> getMessagesById(String id);

    /**
     * 获取该id设备的最近num条数据(便于可视化)
     * @param id
     * @param num
     * @return
     */
    List<IotMessageDO> getMessagesByIdAndNum(String id, Integer num);

    /**
     * 获取最新的一组数据(含所有iot devices)
     * @return
     */
    List<Map<String, IotMessageDO>> getLatestMessages();
}
