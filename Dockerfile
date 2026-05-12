# =========================
# Etapa 1: Build
# =========================
FROM eclipse-temurin:17-jdk AS builder

# Node.js
RUN apt-get update && apt-get install -y curl \
    && curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs

WORKDIR /app

# =========================
# Maven cache
# =========================
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x mvnw

# =========================
# npm cache
# =========================
COPY package.json .
COPY package-lock.json .

RUN npm install

# =========================
# Descargar deps Maven
# =========================
RUN ./mvnw dependency:go-offline -B

# =========================
# Copiar resto proyecto
# =========================
COPY . .

# =========================
# Build final
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
