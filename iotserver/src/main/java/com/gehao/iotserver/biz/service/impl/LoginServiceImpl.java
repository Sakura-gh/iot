package com.gehao.iotserver.biz.service.impl;

import java.util.List;
import java.util.Objects;

import com.gehao.iotserver.biz.bo.Result;
import com.gehao.iotserver.biz.service.LoginService;
import com.gehao.iotserver.dal.dataobject.UserDO;
import com.gehao.iotserver.dal.mapper.UserMapper;
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

    @Override
    public Result login(UserDO requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        String password = requestUser.getPassword();

        Boolean isLegal = false;
        List<UserDO> users = userMapper.getByName(username);
        for (UserDO user : users) {
            if (Objects.equals(password, user.getPassword())) {
                isLegal = true;
                break;
            }
        }
        if (isLegal) {
            return new Result(200);
        } else {
            return new Result(400);
        }
    }
}
