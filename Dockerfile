FROM openjdk:21-jdk

WORKDIR /app

# RUN mvn clean package

COPY target/url-shortener-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
