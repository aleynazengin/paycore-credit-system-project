FROM openjdk:8-jdk-alpine

EXPOSE 8085
ADD target/paycore-credit-system-project-0.0.1-SNAPSHOT.jar paycore-credit-system-project.jar

ENTRYPOINT ["java","-jar","paycore-credit-system-project.jar"]

## Dockerizing the app
#
# Create a Spring Boot Application
# Create Dockerfile
# Build executable jar file - mvn clean package
# Build Docker image - docker build -t paycore-credit-system-project-app:v1 .
# Run Docker container using the image built - docker run -p 8085:8085 paycore-credit-system-project-app:v1
# Test