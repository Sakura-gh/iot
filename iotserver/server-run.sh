#!/usr/bin/env bash
# 运行iot server的脚本，需要等待mysql, redis, emqx的服务起来之后再启动jar包
/wait-for-it.sh mysql:3306 &&
/wait-for-it.sh redis:6379 &&
/wait-for-it.sh emqx:1883 --timeout=60 &&
java -jar /iotserver.jar