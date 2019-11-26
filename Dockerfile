FROM openjdk:8-jre
MAINTAINER Wellington Costa <wellington.costa128@gmail.com>
ADD target/auth-service.jar auth-service.jar
ENTRYPOINT ["java", "-jar", "/auth-service.jar"]