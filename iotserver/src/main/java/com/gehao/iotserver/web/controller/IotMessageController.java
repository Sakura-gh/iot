package com.gehao.iotserver.web.controller;

import java.util.List;

import com.gehao.iotserver.biz.service.IotMessageService;
import com.gehao.iotserver.dal.dataobject.IotMessageDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gehao
 * @date 2021/6/16
 */
@RestController
public class IotMessageController {
    @Autowired
    IotMessageService iotMessageService;

    /**
     * 获取iot数据的总数
     * @return
     */
    @GetMapping("message-total-num")
    public Long messageTotalNum() {
        return iotMessageService.getMessageTotalNum();
    }

    /**
     * 获取当前数据库中所有设备的device id
     * @return
     */
    @GetMapping("device-ids")
    public List<String> deviceIds() {
        return iotMessageService.getDeviceIds();
    }

    @GetMapping("online-device-id")
    public List<String> onlineDeviceIds() {
        return iotMessageService.getOnlineDeviceIds();
    }

    /**
     * 获取该id设备的所有信息记录
     * @param id
     * @return
     */
    @GetMapping("messages-by-id")
    public List<IotMessageDO> messagesById(String id) {
        return iotMessageService.getMessagesById(id);
    }

    /**
     * 获取该id设备的最近num条数据(便于可视化)
     * @param id
     * @param num
     * @return
     */
    @GetMapping("messages-by-id-num")
    public List<IotMessageDO> messagesByIdAndNum(String id, Integer num) {
        return iotMessageService.getMessagesByIdAndNum(id, num);
    }
}
