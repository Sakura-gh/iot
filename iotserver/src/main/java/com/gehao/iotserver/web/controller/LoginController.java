package com.gehao.iotserver.web.controller;

import javax.servlet.http.HttpSession;

import com.gehao.iotserver.biz.bo.Result;
import com.gehao.iotserver.biz.service.impl.LoginServiceImpl;
import com.gehao.iotserver.dal.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gehao
 */
@Controller
public class LoginController {
    @Autowired
    LoginServiceImpl loginService;

    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody UserDO requestUser, HttpSession session) {
        return loginService.login(requestUser, session);
    }
}
