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
     *
     * @param requestUser
     * @return
     */
    Result login(UserDO requestUser, HttpSession session);
}
