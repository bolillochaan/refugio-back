FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copia todos los archivos necesarios para mvnw
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

# Da permisos de ejecución al wrapper
RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Crea directorio y copia certificado con permisos correctos
RUN mkdir -p /app/certs
COPY src/main/resources/ssl/root.crt /app/certs/
RUN chmod 644 /app/certs/root.crt

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
