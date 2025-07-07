# Start from base image
FROM openjdk:17-jdk-alpine

# Set workdir
WORKDIR /app

# Copy the built jar (adjust the path if needed)
COPY build/libs/url-shortener-0.0.1-SNAPSHOT.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
