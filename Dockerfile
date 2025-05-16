# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# Cache dependencies
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Add PostgreSQL client for debugging
RUN apk add --no-cache postgresql-client bash

# Add wait-for-it script for DB readiness
COPY --from=build /app/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

COPY --from=build /app/target/post-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Use shell form to allow environment variable substitution
CMD ["/bin/sh", "-c", "./wait-for-it.sh postgres:5432 --timeout=30 -- java -jar app.jar"]
