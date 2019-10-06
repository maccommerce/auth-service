FROM openjdk:8
ADD target/login-service-0.0.1-SNAPSHOT.jar login-service.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","login-service.jar"]
