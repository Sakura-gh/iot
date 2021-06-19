package com.gehao.iotserver.dal.mapper;

import java.util.List;

import com.gehao.iotserver.dal.BaseMapper;
import com.gehao.iotserver.dal.dataobject.IotMessageDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author gehao
 */
@Repository
public interface IotMessageMapper extends BaseMapper<IotMessageDO> {
    /**
     * 获取记录总数
     * @return
     */
    Long getTotalNum();

    /**
     * 获取当前数据库中所有设备的device id
     * @return
     */
    List<String> getIds();
    /**
     * 获取该id设备的所有信息记录
     * @param id
     * @return
     */
    List<IotMessageDO> getById(@Param("id") String id);

    /**
     * 获取该id设备的最近num条数据(便于可视化)
     * @param id
     * @param num
     * @return
     */
    List<IotMessageDO> getByIdLimits(@Param("id") String id, @Param("num") Integer num);

    /**
     * 用于初始化时清除数据库中原有的所有记录
     */
    void deleteAll();
}
