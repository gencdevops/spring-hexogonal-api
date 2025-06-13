FROM registry.tygoinfra.com/platform/base/image/appsec/java-custom/library:lib-newrelic-21-8.11.0

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/app.jar

WORKDIR /


ENV LANG en_GB.UTF-8
ENV NEW_RELIC_AGENT_ENABLED=false
ENV NEW_RELIC_APP_NAME="stage-localcommerce-ads-reservationapi-service"

EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:/apm/newrelic.jar", "-Dfile.edoncoding=UTF8", "-Duser.timezone=Europe/Istanbul", "-Dspring.backgroundpreinitializer.ignore=$IGNORE_BACKGROUND_INITIALIZER", "-jar", "./app/app.jar"]
