FROM openjdk:17
EXPOSE 8080
RUN mkdir /app
VOLUME /app
COPY ./build/libs/*.jar /app/app.jar
ENV USE_PROFILE server
ENTRYPOINT ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-jar", "/app/app.jar"]