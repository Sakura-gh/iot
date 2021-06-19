package com.gehao.iotserver.dal.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author gehao
 * @date 2021/6/15
 */
@Data
@Accessors(chain = true)
public class IotMessageDO {
    /**
     * 设备ID
     */
    private String clientId;
    /**
     * 上报信息
     */
    private String info;
    /**
     * 设备数据
     */
    private Integer value;
    /**
     * 是否告警，0-正常，1-告警
     */
    private Integer alert;
    /**
     * 设备位置，经度
     */
    private Double lng;
    /**
     * 设备位置，纬度
     */
    private Double lat;
    /**
     * 上报时间，ms
     */
    private Long timestamp;
}
