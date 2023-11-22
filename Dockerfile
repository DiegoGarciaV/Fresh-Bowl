FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/freshbowl.jar /app/freshbowl.jar

COPY src/main/resources/webapp/WEB-INF/video /app/resources/video

CMD ["java", "-jar", "freshbowl.jar"]