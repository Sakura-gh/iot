package com.gehao.iotserver.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gehao.iotserver.dal.dataobject.IotMessageDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<IotMessageDO> dataInfo() {
        List<IotMessageDO> iotMessageList = new ArrayList<>();
        IotMessageDO m1 = new IotMessageDO();
        m1.setClientId("1");
        m1.setInfo("123");
        IotMessageDO m2 = new IotMessageDO();
        m2.setClientId("2");
        m2.setInfo("456");
        IotMessageDO m3 = new IotMessageDO();
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
