FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Instalar cliente PostgreSQL para verificación
RUN apk add --no-cache postgresql-client

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Comando de verificación previa
CMD psql "${SPRING_DATASOURCE_URL#jdbc:postgresql://}" -c "\q" && \
    java -jar app.jar || \
    (echo "Falló la verificación de conexión"; sleep 3600)
