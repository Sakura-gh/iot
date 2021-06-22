package com.gehao.iotserver.biz.service.impl;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import com.gehao.iotserver.biz.bo.Result;
import com.gehao.iotserver.biz.service.LoginService;
import com.gehao.iotserver.biz.service.RedisService;
import com.gehao.iotserver.dal.dataobject.UserDO;
import com.gehao.iotserver.dal.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

/**
 * 记得加@service，否则无法生产bean
 *
 * @author gehao
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    /**
     * 缓存过期时间：10s
     */
    private Integer expireTime = 10;

    public static final String LOGIN = "login";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";



    @Override
    public Boolean login(String username, String password) {
        UserDO user = null;
        String key = LOGIN + username;
        if (redisService.exists(key)) {
            user = JSON.parseObject(redisService.get(key), UserDO.class);
        } else {
            user = userMapper.getByName(username);
            if (user != null) {
                redisService.put(key, JSON.toJSONString(user), expireTime);
            }
        }

        if (user == null || !StringUtils.equals(user.getPassword(), password)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean checkUsername(String username) {
        UserDO user = null;
        String key = USERNAME + username;
        if (redisService.exists(key)) {
            user = JSON.parseObject(redisService.get(key), UserDO.class);
        } else {
            user = userMapper.getByName(username);
            if (user != null) {
                redisService.put(key, JSON.toJSONString(user), expireTime);
            }
        }
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean checkEmail(String email) {
        UserDO user = null;
        String key = EMAIL + email;
        if (redisService.exists(key)) {
            user = JSON.parseObject(redisService.get(key), UserDO.class);
        } else {
            user = userMapper.getByEmail(email);
            if (user != null) {
                redisService.put(key, JSON.toJSONString(user), expireTime);
            }
        }
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean register(String username, String password, String email) {
        UserDO user = new UserDO().setUsername(username)
                                  .setPassword(password)
                                  .setEmail(email);
        userMapper.insert(user);
        return true;
    }

}
