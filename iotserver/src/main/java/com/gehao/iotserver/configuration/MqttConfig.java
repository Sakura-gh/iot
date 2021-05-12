package com.gehao.iotserver.configuration;

import com.gehao.iotserver.mqtt.MqttServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gehao
 */
@Configuration
public class MqttConfig {
    @Value("${iot.broker}")
    private String broker;

    @Value("${iot.topic}")
    private String topic;

    @Value("${iot.timeout}")
    private Integer timeout;

    @Value("${iot.keepAlive}")
    private Integer keepAlive;

    @Value("${iot.isCleanSession}")
    private Boolean isCleanSession;

    @Value("${iot.qos}")
    private Integer qos;

    @Value("${iot.clientID}")
    private String clientID;

    @Bean
    MqttServer getMqttClient() {
        MqttServer mqttServer = new MqttServer();
        mqttServer.setBroker(broker);
        mqttServer.setTopic(topic);
        mqttServer.setTimeout(timeout);
        mqttServer.setKeepAlive(keepAlive);
        mqttServer.setIsCleanSession(isCleanSession);
        mqttServer.setQos(qos);
        mqttServer.setClientID(clientID);
        // 连接
        mqttServer.connect();
        return mqttServer;
    }
}
