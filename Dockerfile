FROM openjdk:17-alpine
COPY target/GitHubApp-0.0.1-SNAPSHOT.jar GitHubApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/GitHubApp-0.0.1-SNAPSHOT.jar"]