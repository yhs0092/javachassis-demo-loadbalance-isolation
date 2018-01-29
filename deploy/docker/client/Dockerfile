FROM registry.cn-north-1.huaweicloud.com/hwcse/dockerhub-java:8-jre-alpine

RUN mkdir -p /maven/demo/

ADD ./client.tgz  /maven/demo/

ENTRYPOINT java $JAVA_OPTS $AK $SK -jar /maven/demo/javachassis-demo-loadbalance-isolation-client-0.0.1-SNAPSHOT.jar
