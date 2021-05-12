package com.gehao.iotserver.biz.service.impl;

import com.gehao.iotserver.biz.service.MqttService;
import com.gehao.iotserver.mqtt.MqttServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gehao
 */
@Service
public class MqttServiceImpl implements MqttService {
    @Autowired
    MqttServer mqttServer;

    @Override
    public boolean publish(String topic, String message) {
        mqttServer.publish(topic, message);
        return true;
    }
}
