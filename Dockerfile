FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
COPY gradle ./gradle
COPY src ./src

RUN chmod +x gradlew

# remove '-x test' to run tests also
RUN ./gradlew clean build -x test 

RUN cp build/libs/Project-0.0.1-SNAPSHOT.jar myapp.jar

EXPOSE 8080
# CMD [ "/bin/sh" ]
CMD ["java", "-jar", "myapp.jar"]
