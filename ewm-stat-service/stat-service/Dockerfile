FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY target/*.jar stat-service.jar
ENTRYPOINT ["java","-jar","/stat-service.jar"]