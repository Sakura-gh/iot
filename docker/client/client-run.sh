#!/usr/bin/env bash
# 运行iot client，模拟iot设备发送数据，需要等待emqx的服务起来之后再启动jar包
/wait-for-it.sh emqx:1883 --timeout=60 &&
java -jar /iotclient.jar