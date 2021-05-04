package com.gehao.iotserver.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gehao
 */
@RestController
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "hello world";
    }
}
