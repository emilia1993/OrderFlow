# Immagine base con Java (Java 17)
FROM eclipse-temurin:17-jre

# Cartella di lavoro dentro il container
WORKDIR /app

# Copia il file JAR dal target del progetto nella cartella /app del container
COPY target/Ordini-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta 8080 (default di Spring Boot)
EXPOSE 8080

# Comando per eseguire il JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
