FROM openjdk:8-jdk-alpine

# MAINTAINER_INFO
MAINTAINER uname.chen@gmail.com

VOLUME /tmp
COPY target/hwc-elb-whitelist-0.0.1-SNAPSHOT.jar /opt/workdir/app.jar

EXPOSE 8000

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/workdir/app.jar"]