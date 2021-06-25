FROM java:8
MAINTAINER gehao<sakura.gehao@gmail.com>
EXPOSE 8443
COPY ./target/iotserver-0.0.1-SNAPSHOT.jar /iotserver.jar
COPY ./wait-for-it.sh /wait-for-it.sh
COPY ./server-run.sh /server-run.sh
RUN chmod +x /server-run.sh
ENTRYPOINT ["./server-run.sh"]