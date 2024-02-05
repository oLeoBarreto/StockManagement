FROM openjdk:17-jdk-slim
LABEL maintainer="Stock Management API by Leo Barreto" version=1.0.0
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]