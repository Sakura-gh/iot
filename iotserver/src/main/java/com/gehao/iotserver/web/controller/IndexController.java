package com.gehao.iotserver.web.controller;

import com.gehao.iotserver.biz.service.impl.MqttServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * for test: 前端访问localhost:8443/getData.json可以获得{"id":1,"username":"gehao","password":"123"}
 *
 * @author gehao
 */
@RestController
public class IndexController {
    @Autowired
    MqttServiceImpl test;

    @GetMapping("test")
    public String test() {
        System.out.println(test.getBroker());
        return "test";
    }
}
