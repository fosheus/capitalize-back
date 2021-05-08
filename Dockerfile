FROM maven:3.8.1-openjdk-11 as build
WORKDIR /opt/capitalize-back
COPY pom.xml .
COPY src ./src
RUN mvn install
RUN find target -name *.jar | xargs -I {} -t mv {} app.jar
RUN ls -l 

FROM adoptopenjdk/openjdk11
WORKDIR /opt/capitalize-back
RUN addgroup --system spring && adduser --system spring --inGroup spring
COPY --from=build /opt/capitalize-back/app.jar .
RUN ["chown","-R","spring:spring","."]
RUN ["chmod","+x","app.jar"]
USER spring:spring
VOLUME /var/log/capitalize/
ENTRYPOINT ["java","-jar","app.jar"]
