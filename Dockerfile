FROM gradle:9.7.1-jdk25-corretto AS build

WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon


FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/student-finance-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
