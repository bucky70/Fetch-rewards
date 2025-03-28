FROM jelastic/maven:3.9.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Runtime Stage
FROM cimg/openjdk:21.0.2-node
WORKDIR /app
COPY --from=build ./target/receiptprocessor-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]
