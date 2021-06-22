package com.gehao.iotserver.dal.mapper;

import java.util.List;

import com.gehao.iotserver.dal.BaseMapper;
import com.gehao.iotserver.dal.dataobject.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author gehao
 */
@Repository
public interface UserMapper extends BaseMapper<UserDO> {
    /**
     * 通过name获取user，用户名唯一
     *
     * @param username
     * @return
     */
    UserDO getByName(@Param("username") String username);

    /**
     * 通过email获取user，邮箱唯一
     *
     * @param email
     * @return
     */
    UserDO getByEmail(@Param("email") String email);
}
