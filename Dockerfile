# Step 1: Build the application with gradlew and JDK 21
FROM eclipse-temurin:21-jdk-jammy AS build

# Set the working directory
WORKDIR /app

# Copy Gradle wrapper and project files
COPY gradlew gradlew
COPY gradle/ gradle/
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY src/ src/


# Build the project dependencies
RUN chmod +x gradlew
RUN ./gradlew bootJar

# Copy the rest of the source files and build the app
COPY . .

# Step 2: Run the application
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Create a non-root user and switch to it
RUN useradd -ms /bin/bash appuser
USER appuser

# Copy the build output from the build stage
COPY --from=build /app/build/libs/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]