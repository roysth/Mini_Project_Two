#Build Angular
FROM node:19 AS angularBuilder

WORKDIR /app

#Copy necessary files
COPY frontend/angular.json .
COPY frontend/package.json .
COPY frontend/package-lock.json .
COPY frontend/tsconfig.app.json .
COPY frontend/tsconfig.json .
COPY frontend/tsconfig.spec.json .
COPY frontend/src ./src

#Install Angular
RUN npm i -g @angular/cli

#Install packages and build
RUN npm i
RUN ng build 


#Build SpringBoot
FROM maven:3.9.0-eclipse-temurin-19 AS springBoot

WORKDIR /app

COPY backend/mvnw .
COPY backend/mvnw.cmd .
COPY backend/pom.xml .
COPY backend/src src

#Copy compiled angular app to static directory
COPY --from=angularBuilder app/dist/client server/src/main/resources/static

RUN mvn package -Dmaven.test.skip=true


#Copy the final Jar file
FROM eclipse-temurin:19-jre

WORKDIR /app

COPY --from=springBoot app/target/server-0.0.1-SNAPSHOT.jar server.jar

ENV PORT=8080

# what port the program is listening to
EXPOSE ${PORT}

ENTRYPOINT java -Dserver.port=${PORT} -jar server.jar

# docker build -t elkayjae/foodjournal:v0 .
# docker run -d -p  8080:8080 elkayjae/foodjournal:v0