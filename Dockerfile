# Use Temurin 21 JDK
FROM eclipse-temurin:21-jdk-alpine

# Non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

# Copy source code
COPY --chown=spring:spring . .

# Build the jar inside the container (assumes Maven)
RUN ./mvnw clean package -DskipTests

# Set the jar file (adjust the path if needed)
COPY target/cloud-storage-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9010

ENTRYPOINT ["java","-jar","app.jar"]
