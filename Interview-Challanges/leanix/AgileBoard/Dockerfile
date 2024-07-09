# Stage 1: Build the application
FROM openjdk:17-jdk-alpine AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . /home/gradle/src
RUN chmod +x ./gradlew
RUN ./gradlew build --no-daemon

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine
EXPOSE 8081
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]