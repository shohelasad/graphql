FROM openjdk:17-alpine3.14

WORKDIR /app

COPY target/seller-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

ADD target/seller-0.0.1-SNAPSHOT.jar app.jar
