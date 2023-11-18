FROM ghcr.io/graalvm/graalvm-community:21 AS build

WORKDIR /app

COPY .mvn .mvn
COPY src src
COPY pom.xml .
COPY mvnw .

RUN chmod +x mvnw
RUN ./mvnw install -DskipTests

FROM ghcr.io/graalvm/graalvm-community:21
LABEL authors="Navjot S. Rakhra"

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]