# syntax=docker/dockerfile:1

# ===== STAGE 1: BUILD =====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Cache de dependências
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Código-fonte e build (gera o .jar mesmo que não exista ainda)
COPY src ./src
RUN mvn -B clean package -DskipTests

# ===== STAGE 2: RUNTIME =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o jar gerado no estágio de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENV PORT=8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
