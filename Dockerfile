# Stage 1
FROM maven:3 as builder

WORKDIR /app
COPY . .
RUN mvn package

FROM openjdk:11-jdk

WORKDIR /app
COPY --from=builder /app/target/*.jar runner.jar
EXPOSE 8080
ENV MONGO_CONNECTION ""
ENTRYPOINT ["java", "spring.data.mongodb.uri=mongodb://${MONGO_CONNECTION}/", "-jar", "runner.jar"]
