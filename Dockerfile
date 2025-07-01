FROM maven:3.9.0-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Crea directorio para certificados (Render montará el secret file aquí)
RUN mkdir -p /app/certs

# Copia el JAR
COPY --from=build /app/target/*.jar app.jar

# Puerto y entrypoint
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
