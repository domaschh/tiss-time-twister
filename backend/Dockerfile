# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim AS build

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
# Add the application's jar to the container
COPY ./target/*.jar app.jar

COPY lvas.txt lvas.txt
COPY bachelor_shorthands.txt bachelor_shorthands.txt
COPY master_softwareengineering_shorthands.txt master_softwareengineering_shorthands.txt
COPY rooms.txt rooms.txt

# Run the jar file
ENTRYPOINT ["java", "-jar","/app.jar"]