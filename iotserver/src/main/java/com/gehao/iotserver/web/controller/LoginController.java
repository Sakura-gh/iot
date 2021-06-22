package com.gehao.iotserver.web.controller;

import com.gehao.iotserver.biz.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gehao
 */
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @GetMapping("login")
    public Boolean login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return loginService.login(username, password);
    }

    @GetMapping("check-username")
    public Boolean checkUsername(@RequestParam("username") String username) {
        return loginService.checkUsername(username);
    }

    @GetMapping("check-email")
    public Boolean checkEmail(@RequestParam("email") String email) {
        return loginService.checkEmail(email);
    }

    @GetMapping("register")
    public Boolean register(@RequestParam("username") String username, @RequestParam("password") String password,
        @RequestParam("email") String email) {
        return loginService.register(username, password, email);
    }
}
