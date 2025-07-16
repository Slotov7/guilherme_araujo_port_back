# Usar uma imagem base oficial do Java 17
FROM openjdk:17-jdk-slim

# Argumento para o ficheiro JAR
ARG JAR_FILE=target/*.jar

# Copiar o ficheiro JAR para dentro do contentor
COPY ${JAR_FILE} app.jar

# Expor a porta que a sua aplicação usa
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java","-jar","/app.jar"]