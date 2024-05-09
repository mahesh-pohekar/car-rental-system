# Stage 1: Build the application (Builder)
FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app

# Copy the generated JAR file to a designated location
COPY target/*.jar app.jar

# Stage 2: Run the application (Runner)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
