# Start with a base image containing Java runtime
FROM eclipse-temurin:23

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
COPY ./target/Order-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]