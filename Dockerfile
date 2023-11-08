FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/freshbowl.jar /app/freshbowl.jar

CMD ["java", "-jar", "freshbowl.jar"]