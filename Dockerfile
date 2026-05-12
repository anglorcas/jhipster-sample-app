# Etapa 1: Compilación
FROM eclipse-temurin:17-jdk AS builder

# Instalar Node.js (capa cacheable, no cambia entre builds)
RUN apt-get update && apt-get install -y curl \
    && curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs

WORKDIR /app

# PRIMERO: copiar solo archivos de dependencias Maven (cambian poco)
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn

# Bajar dependencias compilando (usa repo local para cachear entre capas)
RUN chmod +x mvnw && ./mvnw package -DskipTests --batch-mode -Pprod -Dmaven.repo.local=/app/.m2

# DESPUÉS: copiar el código fuente (cambia en cada push)
COPY . .

# Compilar el .jar final (reutiliza el repo local ya poblado)
RUN ./mvnw package -DskipTests --batch-mode -Pprod -Dmaven.repo.local=/app/.m2

# Etapa 2: Imagen final (solo JRE, más ligera)
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
