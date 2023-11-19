FROM ghcr.io/graalvm/graalvm-community:21 AS build

WORKDIR /app

COPY .mvn .mvn
COPY src src
COPY pom.xml .
COPY mvnw .

RUN chmod +x mvnw
RUN ./mvnw native:compile -Pnative
RUN chmod +x target/app

FROM ubuntu:latest
LABEL authors="Navjot S. Rakhra"

WORKDIR /app

COPY --from=build /app/target/app .

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["./app"]