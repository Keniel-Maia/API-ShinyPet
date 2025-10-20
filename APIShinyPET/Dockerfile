# Multi-stage build mais robusto para Maven + Spring Boot
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copia somente o pom para cache de dependências
COPY pom.xml ./
RUN mvn -B dependency:go-offline

# Copia o código fonte
COPY src ./src

# Build do jar; em seguida copia o primeiro jar encontrado para app.jar
RUN mvn -B clean package -DskipTests \
  && if ls target/*.jar 1> /dev/null 2>&1; then for f in target/*.jar; do cp "$f" app.jar; break; done; else echo "No jar produced"; exit 1; fi

# RUNTIME
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Copia o jar renomeado do estágio de build
COPY --from=build /workspace/app.jar ./app.jar

EXPOSE 8080

# Faça o Spring Boot ler a porta pela variável SERVER_PORT
ENV SERVER_PORT=8080

ENTRYPOINT ["sh","-c","java -Dserver.port=${SERVER_PORT} -jar /app/app.jar"]
