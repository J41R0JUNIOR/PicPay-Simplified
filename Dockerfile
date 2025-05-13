FROM maven:3.9-amazoncorretto-17
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/picpay-0.0.1-SNAPSHOT.jar"]
