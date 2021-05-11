FROM maven:3.8.1-openjdk-11 as build
WORKDIR /opt/capitalize-back
COPY pom.xml .
COPY src ./src
RUN mvn install
RUN find target -name *.jar | xargs -I {} -t mv {} app.jar

FROM adoptopenjdk/openjdk11
WORKDIR /opt/capitalize-back
#RUN addgroup --system spring && adduser --system spring --inGroup spring
COPY --from=build /opt/capitalize-back/app.jar .
#RUN mkdir files && chown -R spring:spring ./files
#RUN mkdir logs && chown -R spring:spring ./logs
VOLUME /opt/capitalize-back/logs/
VOLUME /opt/capitalize-back/conf/
VOLUME /opt/capitalize-back-files/
#USER spring:spring
ENTRYPOINT exec java -Dspring.config.location=/opt/capitalize-back/conf/ -Dcapitalize.file.path=/opt/capitalize-back/files/ -jar ./app.jar
