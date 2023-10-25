FROM maven:3.6.3-openjdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
COPY --from=build /target/AuthenticationSpring-0.0.1-SNAPSHOT.jar AuthenticationSpring.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","AuthenticationSpring.jar"]