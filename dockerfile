FROM eclipse-temurin:17-jdk-alpine AS TEMP_BUILD_IMAGE

ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY .gradle $APP_HOME/.gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build -x test

FROM eclipse-temurin:17-jdk-alpine
ENV ARTIFACT_NAME=lightspeed-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

RUN addgroup demogroup; adduser  --ingroup demogroup --disabled-password demo
RUN chown -R demo:demogroup $APP_HOME
USER demo

CMD ["java", "-jar", "lightspeed-0.0.1-SNAPSHOT.jar"]