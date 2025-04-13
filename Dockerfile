FROM maven:3.9.9-amazoncorretto-11-alpine AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -Dmaven.test.skip=true

COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:23.0.1_11-jre-alpine-3.21 AS runtime

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Duser.timezone=UTC", "-jar", "app.jar"]