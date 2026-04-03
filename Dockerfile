# stage 1: build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY demo/pom.xml .
RUN mvn dependency:go-offline
COPY demo/src ./src
RUN mvn clean package -DskipTests

# stage 2: run
FROM eclipse-temurin:17-jdk-alpine
LABEL maintainer="lamvanchuong"
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 8080