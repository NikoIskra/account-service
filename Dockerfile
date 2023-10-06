FROM amazoncorretto:17.0.8-alpine3.18

ADD app/build/libs/app.jar account-service-docker.jar
ENTRYPOINT [ "java", "-jar", "account-service-docker.jar" ]