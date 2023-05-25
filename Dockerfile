FROM openjdk:11.0-jre

COPY target/CharityService.jar /usr/app/CharityService.jar

WORKDIR /usr/app

CMD java -jar /usr/app/CharityService.jar