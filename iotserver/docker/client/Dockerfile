FROM java:8
MAINTAINER gehao<sakura.gehao@gmail.com>
EXPOSE 8442
COPY ./iotclient-1.0.0.jar /iotclient.jar
COPY ./wait-for-it.sh /wait-for-it.sh
COPY ./client-run.sh /client-run.sh
RUN chmod +x /wait-for-it.sh
RUN chmod +x /client-run.sh
ENTRYPOINT ["./client-run.sh"]