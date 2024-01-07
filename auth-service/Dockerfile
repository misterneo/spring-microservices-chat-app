FROM maven:3.8.3-openjdk-17 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM maven:3.8.3-openjdk-17
COPY --from=build /app/target/auth-api-0.0.1-SNAPSHOT.jar .

ENTRYPOINT [ "java", "-jar", "auth-api-0.0.1-SNAPSHOT.jar" ]