FROM maven:3.8.1-openjdk-11
RUN addgroup --system spring && adduser --system spring --inGroup spring
USER spring:spring
ARG JAR_FILE=target/*.jar
RUN mkdir -p /opt/capitalize-back/
RUN mkdir /var/logs/capitalize/
WORKDIR /opt/capitalize-back
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]