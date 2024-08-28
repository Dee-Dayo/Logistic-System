FROM maven:3.8.7 AS build

COPY . .
RUN mvn -B clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY target/LogisticSystem-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
