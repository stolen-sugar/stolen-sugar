FROM openjdk:latest
FROM gradle:7.4.0-jdk11

WORKDIR /usr/src/app

COPY . ./
RUN gradle bootJar
EXPOSE 8080

CMD ./entrypoint.sh
