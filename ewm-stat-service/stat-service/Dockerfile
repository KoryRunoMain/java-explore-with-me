FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY target/*.jar ewm-stat-service.jar
ENTRYPOINT ["java","-jar","/ewm-stat-service.jar"]