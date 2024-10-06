FROM openjdk:17-jdk-slim
LABEL maintainer="Stock Management API by Leo Barreto" version=2.0.0
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]