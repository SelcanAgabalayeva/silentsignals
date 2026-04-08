# Base image
FROM openjdk:17-jdk-slim

# Jar faylını container-a kopyala
COPY target/silentsignals-0.0.1-SNAPSHOT.jar app.jar

# Java tətbiqini işə sal
ENTRYPOINT ["java","-jar","/app.jar"]