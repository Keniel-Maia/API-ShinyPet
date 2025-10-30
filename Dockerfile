# syntax=docker/dockerfile:1

# Build stage: compile the application and create the executable jar
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy Maven wrapper and project metadata first to leverage Docker layer caching
COPY APIShinyPET/mvnw ./
COPY APIShinyPET/.mvn .mvn
COPY APIShinyPET/pom.xml ./

# Ensure the Maven wrapper script is executable and download dependencies
RUN chmod +x mvnw \
    && ./mvnw -B dependency:go-offline

# Copy the application source code and build the Spring Boot jar
COPY APIShinyPET/src ./src
RUN ./mvnw -B clean package -DskipTests

# Runtime stage: run the application with a lightweight JRE image
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Copy the packaged jar from the build stage
COPY --from=build /app/target/APIShinyPET-0.0.1-SNAPSHOT.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
