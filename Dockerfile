FROM maven:3.8.2-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
COPY --from=build /target/todo-docker.jar todo.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","todo.jar"]