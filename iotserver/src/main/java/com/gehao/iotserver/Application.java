package com.gehao.iotserver;

import java.io.InputStream;
import java.util.Properties;

import com.gehao.iotserver.mqtt.MqttServer;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author gehao
 */
@SpringBootApplication
@MapperScan("com.gehao.iotserver.dal.mapper")
public class Application {

	public static void main(String[] args) {
		try {
			MqttServer mqttServer = new MqttServer();
			Properties properties = new Properties();
			InputStream in = Application.class.getClassLoader().getResourceAsStream("application.properties");
			properties.load(in);
			String broker = properties.getProperty("iot.broker");
			String topic = properties.getProperty("iot.topic");
			Integer timeout = Integer.valueOf(properties.getProperty("iot.timeout"));
			Integer keepAlive = Integer.valueOf(properties.getProperty("iot.keepAlive"));
			Boolean isCleanSession = Boolean.valueOf(properties.getProperty("iot.isCleanSession"));
			Integer qos = Integer.valueOf(properties.getProperty("iot.qos"));
			String clientID = properties.getProperty("iot.clientID");
			mqttServer.setBroker(broker);
			mqttServer.setTopic(topic);
			mqttServer.setTimeout(timeout);
			mqttServer.setKeepAlive(keepAlive);
			mqttServer.setIsCleanSession(isCleanSession);
			mqttServer.setQos(qos);
			mqttServer.setClientID(clientID);
			mqttServer.connect();
		} catch (Exception e) {
			System.out.println("load properties error");
		}
		SpringApplication.run(Application.class, args);
	}

}
