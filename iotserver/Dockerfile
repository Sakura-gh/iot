FROM java:8
MAINTAINER gehao<sakura.gehao@gmail.com>
EXPOSE 8443
COPY target/iotserver-0.0.1-SNAPSHOT.jar /iotserver.jar
ENTRYPOINT ["java", "-jar", "/iotserver.jar"]