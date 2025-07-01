# Guía de Despliegue en Render - Backend Refugio de Animales

## 🚀 Pasos para Desplegar en Render

### Opción 1: Despliegue desde GitHub (Recomendado)

#### 1. Preparar el Repositorio
```bash
# Asegúrate de que tu código esté en GitHub
git add .
git commit -m "Preparando para despliegue en Render"
git push origin main
```

#### 2. Conectar con Render
1. Ve a [render.com](https://render.com) y crea una cuenta
2. Haz clic en "New +" y selecciona "Web Service"
3. Conecta tu repositorio de GitHub
4. Selecciona tu repositorio `Refugio`

#### 3. Configurar el Servicio
- **Name**: `refugio-backend`
- **Environment**: `Docker`
- **Region**: `Oregon` (o la más cercana a ti)
- **Branch**: `main`
- **Root Directory**: (dejar vacío)
- **Build Command**: `mvn clean package -DskipTests`
- **Start Command**: `java -jar target/*.jar`

#### 4. Configurar Variables de Entorno
En la sección "Environment Variables":
```
SPRING_PROFILES_ACTIVE=production
PORT=8080
DATABASE_URL=tu_url_de_base_de_datos
DATABASE_USERNAME=tu_usuario
DATABASE_PASSWORD=tu_password
ADMIN_USERNAME=admin
ADMIN_PASSWORD=tu_password_seguro
ALLOWED_ORIGINS=https://tu-frontend.netlify.app
JWT_SECRET=tu_jwt_secret_muy_seguro
```

### Opción 2: Usando render.yaml (Blueprint)

#### 1. Usar el archivo render.yaml
Si tienes el archivo `render.yaml` en tu repositorio:
1. Ve a Render Dashboard
2. Haz clic en "New +" > "Blueprint"
3. Conecta tu repositorio
4. Render detectará automáticamente el archivo y configurará todo

### Opción 3: Despliegue Manual

#### 1. Construir la Aplicación
```bash
mvn clean package -DskipTests
```

#### 2. Crear un Docker Image
```bash
docker build -t refugio-backend .
```

#### 3. Subir a Render
1. En Render, crea un nuevo "Web Service"
2. Selecciona "Deploy from Docker image"
3. Sube tu imagen Docker

## 🗄️ Configuración de Base de Datos

### Opción 1: Base de Datos en Render
1. En Render Dashboard, crea un nuevo "PostgreSQL"
2. Configura las credenciales
3. Copia la URL de conexión
4. Agrega las variables de entorno en tu servicio web

### Opción 2: Base de Datos Externa
- **YugabyteDB Cloud**: Si usas YugabyteDB
- **PostgreSQL**: Cualquier proveedor (Heroku Postgres, AWS RDS, etc.)
- **MySQL**: Si prefieres MySQL

### Variables de Entorno para Base de Datos
```
DATABASE_URL=jdbc:postgresql://host:port/database
DATABASE_USERNAME=tu_usuario
DATABASE_PASSWORD=tu_password
```

## 🔧 Configuraciones Adicionales

### 1. Health Check
El endpoint `/api/health` ya está configurado para Render:
- Verifica la conexión a la base de datos
- Retorna el estado del servicio
- Render lo usa para monitorear la salud del servicio

### 2. CORS Configuration
En `application-prod.properties`:
```properties
spring.web.cors.allowed-origins=${ALLOWED_ORIGINS:https://tu-frontend.netlify.app}
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
spring.web.cors.allow-credentials=true
```

### 3. Logging
Configurado para producción:
- Solo logs importantes
- Sin SQL queries en producción
- Logs estructurados para monitoreo

## 📊 Monitoreo y Analytics

### 1. Render Dashboard
- Monitorea el rendimiento del servicio
- Revisa logs en tiempo real
- Configura alertas

### 2. Health Checks
- Render verifica automáticamente `/api/health`
- Reinicia el servicio si falla
- Notificaciones por email

### 3. Logs
- Accede a logs en tiempo real
- Filtra por nivel de log
- Exporta logs para análisis

## 🚨 Solución de Problemas Comunes

### Error: Build Failed
```bash
# Verifica que Maven funcione localmente
mvn clean package -DskipTests

# Revisa los logs de build en Render
```

### Error: Database Connection Failed
- Verifica las variables de entorno
- Asegúrate de que la base de datos esté accesible
- Revisa las credenciales

### Error: Port Already in Use
- Render maneja automáticamente el puerto
- Usa la variable `PORT` de Render
- No configures puerto fijo

### Error: Memory Issues
- Ajusta `JAVA_OPTS` en el Dockerfile
- Considera usar un plan con más memoria
- Optimiza la aplicación

## 🔄 Despliegues Automáticos

### Auto-Deploy
- Render despliega automáticamente en cada push
- Configura branch protection en GitHub
- Usa pull requests para cambios importantes

### Manual Deploy
- Puedes hacer deploy manual desde Render Dashboard
- Útil para rollbacks rápidos
- Pruebas antes de producción

## 📱 Optimización para Producción

### 1. Performance
- JVM optimizado para Render
- Caché configurado
- Logging optimizado

### 2. Security
- Variables de entorno para credenciales
- CORS configurado
- HTTPS automático

### 3. Monitoring
- Health checks automáticos
- Logs estructurados
- Métricas de rendimiento

## 🔗 Integración con Frontend

### 1. Actualizar Frontend
En tu frontend, actualiza la URL del API:
```typescript
// environment.prod.ts
export const environment = {
  production: true,
  apiUrl: 'https://tu-backend.onrender.com/api'
};
```

### 2. CORS Configuration
Asegúrate de que CORS permita tu dominio de Netlify:
```
ALLOWED_ORIGINS=https://tu-app.netlify.app
```

## 🎉 ¡Listo!

Una vez completados estos pasos, tu backend estará desplegado en Render y accesible desde internet. El dominio será algo como: `https://refugio-backend.onrender.com`

### URLs Útiles
- **Dashboard de Render**: https://dashboard.render.com
- **Documentación**: https://render.com/docs
- **Soporte**: https://render.com/support

### Endpoints de Prueba
- **Health Check**: `GET /api/health`
- **Test Connection**: `GET /api/test/connection`
- **API Base**: `GET /api/animales`

### Próximos Pasos
1. Configura tu base de datos
2. Actualiza las variables de entorno
3. Haz el primer deploy
4. Prueba los endpoints
5. Conecta con tu frontend 