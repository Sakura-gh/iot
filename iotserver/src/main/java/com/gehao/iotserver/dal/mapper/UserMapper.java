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
     * 通过name获取user，可能存在同名现象，因此返回集合
     *
     * @param name
     * @return
     */
    List<UserDO> getByName(@Param("name") String name);

}
