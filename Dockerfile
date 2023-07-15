FROM openjdk:11

MAINTAINER "Griotold"

COPY /build/libs/*.jar /app.jar

EXPOSE 8081

CMD ["java", "-jar", "/app.jar"]
# CMD ["java", "-Dspring.profiles.active=원하는 프로파일", "-jar", "app.jar"]