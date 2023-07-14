FROM tomcat:10.1.11-jdk11-temurin-jammy

MAINTAINER "Griotold"

COPY /build/libs/*.jar /app.jar

EXPOSE 8081

CMD ["java", "-jar", "/app.jar"]