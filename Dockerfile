# Etapa 1: Compilación
FROM eclipse-temurin:17-jdk AS builder

# Instalar Node.js (se cachea, no cambia)
RUN apt-get update && apt-get install -y curl \
    && curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs

WORKDIR /app

# PRIMERO: copiar solo los archivos de dependencias (cambia poco)
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn

# Dar permisos y bajar dependencias (capa cacheable)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -DskipTests --batch-mode

# DESPUÉS: copiar el código fuente (esto cambia siempre)
COPY . .

# Compilar (usa las dependencias ya cacheadas)
RUN ./mvnw package -DskipTests --batch-mode -Pprod

# Etapa 2: Imagen final (solo JRE, más ligera)
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
