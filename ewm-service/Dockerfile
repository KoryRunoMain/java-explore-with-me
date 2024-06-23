FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY target/*.jar ewm-service.jar
ENTRYPOINT ["java","-jar","/ewm-service.jar"]