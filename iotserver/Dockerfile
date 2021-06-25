FROM java:8
MAINTAINER gehao<sakura.gehao@gmail.com>
EXPOSE 8443
COPY target/iotserver-0.0.1-SNAPSHOT.jar /iotserver.jar
COPY wait-for-it.sh /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "emqx:1883", "--timeout=60", "--", "java", "-jar", "/iotserver.jar"]