package com.gehao.iotserver.mqtt;

import com.alibaba.fastjson.JSON;

import com.gehao.iotserver.biz.service.IotMessageService;
import com.gehao.iotserver.dal.dataobject.IotMessageDO;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gehao
 */
@Data
public class MqttServer {
    @Autowired
    IotMessageService iotMessageService;

    private String broker;

    private String topic;

    private Integer timeout;

    private Integer keepAlive;

    private Boolean isCleanSession;

    private Integer qos;

    private String clientID;

    private MqttClient mqttClient;

    public void connect() {
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            mqttClient = new MqttClient(broker, clientID, persistence);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(isCleanSession);
            connectOptions.setConnectionTimeout(timeout);
            connectOptions.setKeepAliveInterval(keepAlive);

            // 设置回调函数，用于处理接收到的消息
            mqttClient.setCallback(new SubCallback());

            System.out.println("准备连接到broker：" + broker);
            mqttClient.connect(connectOptions);
            System.out.println("连接成功！");

            // 订阅消息
            subscribe();
        } catch (Exception e) {
            System.out.println("connect to broker error!");
        }
    }

    public boolean publish(String topic, String message) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        mqttMessage.setQos(qos);
        System.out.println("publish message: " + message);
        if (mqttClient == null) {
            connect();
        }
        try {
            mqttClient.publish(topic, mqttMessage);
            System.out.println("publish success!");
        } catch (Exception e) {
            System.out.println("publish error!");
        }
        return true;
    }

    private void subscribe() {
        try {
            System.out.println("准备订阅主题：" + topic);
            mqttClient.subscribe(topic);
            System.out.println("订阅成功！");
        } catch (Exception e) {
            System.out.println("subscribe error!");
        }
    }

    private class SubCallback implements MqttCallback {
        @Override
        public void connectionLost(Throwable cause) {
            if (mqttClient == null) {
                System.out.println("连接断开，正在重连...");
                connect();
                System.out.println("已重连");
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            String msg = new String(message.getPayload());
            System.out.println("接收到topic " + topic + "的消息: " + msg);
            IotMessageDO iotMessage = JSON.parseObject(msg, IotMessageDO.class);
            // 将iot数据插入数据库
            iotMessageService.insertMessage(iotMessage);
        }
    }
}
