# Étape 1 : Builder avec Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Image finale
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app-client.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app-client.jar"]
