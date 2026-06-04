# Stage 1: Build the JAR inside the container
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
WORKDIR /app

# Copy dependency definition and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the production jar (skipping test suite for speed)
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime image
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/PortfolioApi-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]