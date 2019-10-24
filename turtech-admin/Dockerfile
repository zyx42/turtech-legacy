FROM openjdk:8-jdk-alpine

VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ADD target/turtech-admin-0.0.2-SNAPSHOT.jar turtech-admin.jar
EXPOSE 9091
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar turtech-admin.jar