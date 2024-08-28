FROM maven:3.8.7 AS build

COPY . .
RUN mvn -B clean package -DskipTests

FROM openjdk:17
COPY --from=build ./target/*.jar deelogistics.jar

ENTRYPOINT ["java", "-jar","-Dserver.port=8080", "deelogistics.jar"]