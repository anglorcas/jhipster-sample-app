# =========================
# Etapa 1: Build
# =========================
FROM eclipse-temurin:17-jdk AS builder

# Instalar Node.js
RUN apt-get update && apt-get install -y curl \
    && curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs

WORKDIR /app

# =========================
# Maven wrapper y config
# =========================
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x mvnw

# =========================
# npm cache layer
# =========================
COPY package.json .
COPY package-lock.json .

RUN npm ci

# =========================
# Copiar resto del proyecto
# =========================
COPY . .

# =========================
# Build
# =========================
RUN ./mvnw package -DskipTests --batch-mode -Pprod

# =========================
# Runtime
# =========================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
