# Build stage
FROM maven:3.9.6-ibm-semeru-17-focal as build
COPY ./src /buildDir/src
COPY ./pom.xml /buildDir
WORKDIR /buildDir
RUN mvn package

# Package stage
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /buildDir/target/SpringBoot-Forward-Proxy.jar /app/SpringBoot-Forward-Proxy.jar
ENTRYPOINT java -jar /app/SpringBoot-Forward-Proxy.jar 

