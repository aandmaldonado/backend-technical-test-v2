FROM openjdk:11-jdk-slim

WORKDIR /app

COPY build/libs/backend-technical-test-v2-master-2.0.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]