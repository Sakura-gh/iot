package com.gehao.iotserver.biz.service;

import javax.servlet.http.HttpSession;

import com.gehao.iotserver.biz.bo.Result;
import com.gehao.iotserver.dal.dataobject.UserDO;

/**
 * @author gehao
 */
public interface LoginService {
    /**
     * 登录功能
     * @param username
     * @param password
     * @return
     */
    Boolean login(String username, String password);

    /**
     * 校验用户名
     * @param username
     * @return
     */
    Boolean checkUsername(String username);

    /**
     * 校验邮箱
     * @param email
     * @return
     */
    Boolean checkEmail(String email);

    /**
     * 注册新账户
     * @param username
     * @param password
     * @param email
     * @return
     */
    Boolean register(String username, String password, String email);
}
