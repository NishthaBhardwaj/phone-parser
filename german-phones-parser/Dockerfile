FROM openjdk:11-slim as build
VOLUME /tmp
EXPOSE 8080
ADD target/german-phones-parser-rest-api.jar german-phones-parser-rest-api.jar
ENTRYPOINT [ "sh" ,"-c" , "java -jar /german-phones-parser-rest-api.jar" ]