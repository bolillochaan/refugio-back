FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Crear directorio y copiar certificado con permisos
RUN mkdir -p /app/certs
COPY src/main/resources/ssl/root.crt /app/certs/
RUN chmod 644 /app/certs/root.crt

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
