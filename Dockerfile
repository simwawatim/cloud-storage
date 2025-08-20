# Use Temurin 21 JDK
FROM eclipse-temurin:21-jdk-alpine

# Non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app
COPY target/cloud-storage-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9010

ENTRYPOINT ["java","-jar","app.jar"]
