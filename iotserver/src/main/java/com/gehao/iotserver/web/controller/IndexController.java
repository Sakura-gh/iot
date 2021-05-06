package com.gehao.iotserver.web.controller;

import com.gehao.iotserver.dal.dataobject.UserDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * for test: 前端访问localhost:8443/getData.json可以获得{"id":1,"username":"gehao","password":"123"}
 *
 * @author gehao
 */
@RestController
public class IndexController {
    @GetMapping("/getData.json")
    public UserDO index() {
        UserDO user = new UserDO();
        user.setId(1);
        user.setUsername("gehao");
        user.setPassword("123");
        return user;
    }
}
