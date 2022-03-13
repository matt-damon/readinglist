FROM openjdk:8-jdk-alpine
ENV SPRING_PROFILES_ACTIVE development
RUN apk add --no-cache bash procps nano
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
COPY bash/ /mybash
ENTRYPOINT ["java",\
            "-jar",\
            "/app.jar"]