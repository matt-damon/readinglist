FROM openjdk:8-jdk-alpine
ENV SPRING_PROFILES_ACTIVE=development PS1='[\u@\h:$PWD]'
RUN apk add --no-cache bash procps nano make gawk wget \
    && wget -q https://dlcdn.apache.org/zookeeper/zookeeper-3.7.0/apache-zookeeper-3.7.0-bin.tar.gz \
    && tar -xzf apache-zookeeper-3.7.0-bin.tar.gz -C opt \
    && mv opt/apache-zookeeper-3.7.0-bin opt/zookeeper \
    && mv opt/zookeeper/conf/zoo_sample.cfg opt/zookeeper/conf/zoo.cfg
EXPOSE 8081 2181 2888 3888
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
COPY bash/ /mybash
#ENTRYPOINT ["java",\
#            "-jar",\
#            "/app.jar"]D