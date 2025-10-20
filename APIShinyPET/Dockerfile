# syntax=docker/dockerfile:1

# ===== STAGE 1: BUILD =====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1) Cache de dependências
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline || true

# 2) Código-fonte + build
COPY src ./src
RUN mvn -B clean package -DskipTests

# 3) Normaliza o nome do artefato para um nome único (app.jar)
#    Evita problemas com múltiplos JARs no target/ e com COPY com wildcard
RUN set -eux; \
    JAR="$(ls -1 target/*.jar | head -n 1)"; \
    cp "$JAR" app.jar

# ===== STAGE 2: RUNTIME =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o JAR já normalizado
COPY --from=build /app/app.jar /app/app.jar

EXPOSE 8080
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
