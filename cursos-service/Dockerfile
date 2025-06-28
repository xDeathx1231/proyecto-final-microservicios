# Usa la imagen oficial de Java 17
FROM eclipse-temurin:17-jdk

# Define el argumento del JAR
ARG JAR_FILE=target/*.jar

# Copia el JAR al contenedor
COPY ${JAR_FILE} app.jar

# Comando para ejecutar
ENTRYPOINT ["java","-jar","/app.jar"]
