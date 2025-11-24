# Builder
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Image finale
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app-transport.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app-transport.jar"]
