FROM amazoncorretto:11-alpine-jdk
COPY target/GitHubApp-0.0.1-SNAPSHOT.jar GitHubApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/GitHubApp-0.0.1-SNAPSHOT.jar"]