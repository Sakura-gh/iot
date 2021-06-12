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

    /**
     * 只有放在@Configuration里，才能在创建Bean的时刻正确读到.properties文件里的数据，
     * 其余component只有在创建完Bean之后才能读到.properties数据，创建时刻数据为null
     *
     * 这里@Configuration的作用是创建mqttServer对象并交给Spring容器，顺便读取参数连接emqx broker
     * 考虑到程序一开始运行就要进行连接，否则读取数据会缺失，而只有@Configuration才能在创建Bean的时刻
     * 读取.properties参数并进行broker的连接
     *
     * @return
     */
    @Bean
    MqttServer mqttServer() {
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
