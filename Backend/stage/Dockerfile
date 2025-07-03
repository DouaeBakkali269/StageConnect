FROM eclipse-temurin:17-jre-jammy
COPY target/*.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "/app.jar"]