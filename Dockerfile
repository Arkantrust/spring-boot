FROM maven:3-amazoncorretto-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src/ ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine AS run

RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser

WORKDIR /app

COPY --from=build /app/target/*.jar ./server.jar

# Least privilege principle
RUN chown -R javauser:javauser .

USER javauser

CMD ["java", "-jar", "server.jar"]
