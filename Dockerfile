# Usar OpenJDK 17 como base
FROM openjdk:17-jdk-slim

# Instalar curl para descargar certificados
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Crear directorio para certificados
RUN mkdir -p /app/certs

# Descargar certificado de YugabyteDB
RUN curl -o /app/certs/root.crt https://s3.us-west-2.amazonaws.com/downloads.yugabyte.com/certs/root.crt

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR construido
COPY target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Variables de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
