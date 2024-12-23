FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/JenkinsTp-0.0.1-SNAPSHOT.jar /app/JenkinsTp.jar

ENTRYPOINT ["java", "-jar", "JenkinsTp.jar"]

EXPOSE 8080