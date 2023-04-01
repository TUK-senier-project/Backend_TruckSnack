#JDK
FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#ElasticSearch
#FROM docker.elastic.co/elasticsearch/elasticsearch:8.6.2
