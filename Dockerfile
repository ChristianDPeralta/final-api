# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Add PostgreSQL client and download wait-for-it directly
RUN apk add --no-cache postgresql-client bash wget && \
    wget -O /wait-for-it.sh https://github.com/vishnubob/wait-for-it/raw/master/wait-for-it.sh && \
    chmod +x /wait-for-it.sh

COPY --from=build /app/target/post-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["/bin/sh", "-c", "/wait-for-it.sh postgres:5432 --timeout=30 -- java -jar app.jar"]
