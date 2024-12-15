# Build stage
FROM openjdk:17 as build

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM openjdk:17

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]