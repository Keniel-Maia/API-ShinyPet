# syntax=docker/dockerfile:1

# ==========================
#  STAGE 1: BUILD
# ==========================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o POM primeiro (cache das dependências)
COPY APIShinyPET/pom.xml ./
RUN mvn -B dependency:go-offline

# Copia o código-fonte
COPY APIShinyPET/src ./src

# Compila o projeto e cria o .jar (mesmo que ainda não exista)
RUN if [ ! -f target/*.jar ]; then mvn -B clean package -DskipTests; fi

# ==========================
#  STAGE 2: RUNTIME
# ==========================
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Copia o .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Define a variável de porta usada pelo Spring
ENV PORT=8080

# Inicia o aplicativo
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
