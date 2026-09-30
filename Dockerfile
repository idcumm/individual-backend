# Compilar
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon


# Execute
# Light linux build
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/student-finance-api-0.0.1-SNAPSHOT.jar app.jar
# Linux exec()
ENTRYPOINT ["java", "-jar", "app.jar"]
