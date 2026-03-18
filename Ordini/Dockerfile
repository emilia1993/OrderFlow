# Base image with Java (Java 17)
FROM eclipse-temurin:17-jre

# Working directory inside the container
WORKDIR /app

# Copy the JAR file from the project's target folder to the /app directory in the container
COPY target/orderflow-1.0.1.jar app.jar

# Expose port 8080 (default Spring Boot port)
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
