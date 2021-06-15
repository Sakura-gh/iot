package com.gehao.iotserver.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.gehao.iotserver.biz.bo.IotMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * for test: 前端访问localhost:8443/getData.json可以获得{"id":1,"username":"gehao","password":"123"}
 *
 * @author gehao
 */
@RestController
public class IndexController {

    @GetMapping("test")
    public String test() {
        return "test";
    }

    @GetMapping("datainfo")
    public List<IotMessage> dataInfo() {
        List<IotMessage> iotMessageList = new ArrayList<>();
        IotMessage m1 = new IotMessage();
        m1.setClientId("1");
        m1.setInfo("123");
        IotMessage m2 = new IotMessage();
        m2.setClientId("2");
        m2.setInfo("456");
        IotMessage m3 = new IotMessage();
        m3.setClientId("3");
        m3.setInfo("789");
        iotMessageList.add(m1);
        iotMessageList.add(m2);
        iotMessageList.add(m3);
        return iotMessageList;
    }

    @GetMapping("online-status")
    public int onlineStatus() {
        Random random = new Random();
        return random.nextInt(20);
    }
}
