#!/usr/bin/env bash
# 运行iot server，需要等待mysql, redis, emqx的服务起来之后再启动jar包
/wait-for-it.sh mysql:3306 &&
/wait-for-it.sh redis:6379 &&
/wait-for-it.sh emqx:1883 --timeout=60 &&
java -jar /iotserver.jar

# 运行iot client，模拟iot设备发送数据 
java -jar /iotclient.jar