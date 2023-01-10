FROM openjdk:11.0.11-jre-slim
ARG JAR_FILE=target/jesse-1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 9000