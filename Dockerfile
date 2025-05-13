FROM maven:3.9.9-eclipse-temurin-24-alpine
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/picpay-0.0.1-SNAPSHOT.jar"]
