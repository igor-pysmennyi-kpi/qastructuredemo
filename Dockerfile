FROM amazoncorretto:21.0.1
ARG ARTIFACT_NAME=`testterteste*`
ADD target/${ARTIFACT_NAME}.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]