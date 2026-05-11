# Etapa 1: Compilación
FROM eclipse-temurin:17-jdk AS builder

# Instalar Node.js
RUN apt-get update && apt-get install -y curl \
    && curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs

WORKDIR /app
COPY . .

# Dar permisos y compilar
RUN chmod +x mvnw && ./mvnw -ntp verify -DskipTests --batch-mode -Pprod

# Etapa 2: Imagen final (solo JRE, más ligera)
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]