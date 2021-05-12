package com.gehao.iotserver.biz.service;

/**
 * @author gehao
 */
public interface MqttService {
    /**
     * 发布消息
     *
     * @param topic
     * @param message
     * @return
     */
    boolean publish(String topic, String message);
}
