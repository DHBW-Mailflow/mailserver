#
# Build stage
#
FROM maven:3.9.5-eclipse-temurin-17-focal AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
RUN ls /home/app/target/

#
# Package stage
#
FROM eclipse-temurin:17
COPY --from=build /home/app/target/mailflow-1.0-SNAPSHOT.jar /usr/local/lib/mailflow.jar
EXPOSE 587
EXPOSE 465
ENTRYPOINT ["java","-jar","/usr/local/lib/mailflow.jar"]