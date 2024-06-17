FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY target/*.jar stats-server.jar
ENTRYPOINT ["java","-jar","/stats-server.jar"]