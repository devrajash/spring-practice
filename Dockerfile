FROM maven:3.8.3-openjdk-17-slim

COPY . .
RUN mvn clean install -DskipTests
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/springboot1-0.0.1-SNAPSHOT.jar"]
