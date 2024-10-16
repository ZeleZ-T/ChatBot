FROM openjdk:22-jdk

ARG JAR_FILE=build/libs/*.jar

COPY build/libs/*.jar chat_bot_api.jar
COPY storage storage

ENTRYPOINT ["java", "-jar", "/chat_bot_api.jar"]