FROM anapsix/alpine-java
MAINTAINER Eduardo Filho <filhoeduardo83@gmail.com>
ADD target/auth-service.jar auth-service.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","auth-service.jar"]