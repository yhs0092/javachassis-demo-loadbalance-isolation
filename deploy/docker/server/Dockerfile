FROM registry.cn-north-1.huaweicloud.com/hwcse/dockerhub-java:8-jre-alpine

RUN mkdir -p /maven/demo/

COPY ./javachassis-demo-loadbalance-isolation-server-0.0.1-SNAPSHOT.jar  /maven/demo/

ENTRYPOINT java $JAVA_OPTS $AK $SK -jar /maven/demo/javachassis-demo-loadbalance-isolation-server-0.0.1-SNAPSHOT.jar
