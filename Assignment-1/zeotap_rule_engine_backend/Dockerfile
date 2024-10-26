FROM openjdk:21-jdk


WORKDIR /app

# Specify the exact JAR file if needed
ARG JAR_FILE=target/zeotap_rule_engine-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
