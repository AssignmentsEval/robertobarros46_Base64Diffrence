FROM openjdk:11-jre-slim
MAINTAINER "JsonBase64Difference app"
WORKDIR /app

COPY ./target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8080