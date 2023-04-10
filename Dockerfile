# Stage 1
FROM maven:3 as builder

WORKDIR /app
COPY . .
RUN mvn package 

FROM openjdk:11-jdk

WORKDIR /home/app
RUN useradd -ms /bin/bash -u 999 app
USER app
COPY --from=builder /app/target/*.jar runner.jar
EXPOSE 8080
ENV MONGO_CONNECTION ""
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=${MONGO_CONNECTION}", "-jar", "runner.jar"]
