FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY target/AccountService-0.0.1-SNAPSHOT.jar /app/AccountService-0.0.1-SNAPSHOT.jar

EXPOSE ${REST_SERVER_PORT}

ENV CONFIG_SERVER_PASSWORD=${CONFIG_SERVER_PASSWORD}
ENV CONFIG_SERVER_USERNAME=${CONFIG_SERVER_USERNAME}
ENV EUREKA_URL=${EUREKA_URL}
ENV ACTIVE_PROFILE=${ACTIVE_PROFILE}
ENV EXTERNAL_IP=${EXTERNAL_IP}

ENTRYPOINT ["java", "-jar", "/app/AccountService-0.0.1-SNAPSHOT.jar"]