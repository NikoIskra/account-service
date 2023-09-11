FROM amazoncorretto:17.0.8-alpine3.18

ADD app/build/libs/app.jar springboot-docker-demo.jar
ENTRYPOINT [ "java", "-jar", "springboot-docker-demo.jar" ]