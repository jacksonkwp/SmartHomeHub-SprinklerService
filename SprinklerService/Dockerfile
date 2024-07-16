FROM eclipse-temurin:21-jdk-alpine
LABEL authors="Pawan_Bhandari1@baylor.edu"

WORKDIR /workspace/app
COPY target/*.jar sprinkler-service.jar

ENTRYPOINT ["java", "-jar", "sprinkler-service.jar"]