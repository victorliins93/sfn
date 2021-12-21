FROM maven:3.8.4-jdk-11 AS build

WORKDIR /sfn

COPY pom.xml /sfn/pom.xml
RUN mvn dependency:go-offline

COPY src /sfn/src
RUN mvn package -DskipTests=true

FROM openjdk:11

WORKDIR /sfn

COPY --from=build /sfn/target/*.jar sfn_api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sfn_api.jar"]
