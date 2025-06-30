FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Configuración robusta del certificado
RUN mkdir -p /app/certs
COPY src/main/resources/ssl/root.crt /app/certs/
RUN chmod 644 /app/certs/root.crt && \
    apk add --no-cache postgresql-client && \
    psql "${DATABASE_URL#jdbc:postgresql://}" -c "\q" || echo "Pre-check failed, continuing..."

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
