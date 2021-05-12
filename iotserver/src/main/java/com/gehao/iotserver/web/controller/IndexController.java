package com.gehao.iotserver.web.controller;

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
}
