
#Stage 1: Crear .jar 
FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder

# Define el directorio de trabajo donde ejecutar comandos
WORKDIR /project

# Copia las dependencias del proyecto
COPY pom.xml /project/
COPY keystore.jks /project/keystore.jks

# Descarga las dependencias del proyecto
RUN mvn dependency:go-offline

# Copia el código del proyecto
COPY . /project/

# Compila proyecto
RUN mvn -B package -DskipTests

# Stage 2: Usa eclipse y java para compilar y ejecutar el .jar 
FROM eclipse-temurin:21-jre

# Define el directorio de trabajo donde se encuentra el JAR
WORKDIR /usr/src/app/

# Copia el JAR del contenedor de compilación
COPY --from=builder /project/target/ssddgrupo13-1.0.0.jar /usr/src/app/app.jar
COPY --from=builder /project/keystore.jks keystore.jks  


# Indica el puerto que expone el contenedor
EXPOSE 8443

# Comando que se ejecuta al hacer docker run
CMD [ "java", "-jar", "app.jar" ]