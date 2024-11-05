FROM openjdk:17-jdk-alpine
EXPOSE 8082
ADD target/Devops-1.0jar Devops-1.0jar
ENTRYPOINT ["java","DevOps-Project-jar","/DevOps-Project.jar"]