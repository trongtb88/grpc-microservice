FROM openjdk:8-jdk-alpine
LABEL maintainer="trongtb1988@gmail.com"
ADD target/eurekaserver-0.0.1.jar eurekaserver.jar
EXPOSE 8761
ENTRYPOINT java -jar eurekaserver.jar eurekaserver
