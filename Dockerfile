# Use Temurin 21 JDK
FROM eclipse-temurin:21-jdk-alpine

# Non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

# Copy all source code
COPY --chown=spring:spring . .

# Build the JAR inside the container (requires Maven wrapper)
RUN ./mvnw clean package -DskipTests

# Run the JAR
ENTRYPOINT ["java","-jar","target/cloud-storage-0.0.1-SNAPSHOT.jar"]

EXPOSE 9010
