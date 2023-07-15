FROM openjdk:11

MAINTAINER "Griotold"

COPY /build/libs/*.jar /app.jar

EXPOSE 5000

# CMD ["java", "-jar", "/app.jar"]
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]