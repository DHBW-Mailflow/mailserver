#
# Build stage
#
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:21
COPY --from=build /home/app/target/mailflow-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/lib/mailflow.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/mailflow.jar"]