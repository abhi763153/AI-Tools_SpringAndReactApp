# Stage 1: Build React Frontend
FROM node:18 AS frontend-build
WORKDIR /app
# Copy React files
COPY spring-ai-demo-react/package*.json ./
RUN npm install
COPY spring-ai-demo-react/ .
RUN npm run build

# Stage 2: Build Spring Boot Backend
FROM eclipse-temurin:21-jdk AS backend-build
WORKDIR /app
# Copy Maven files
COPY SpringAI-Backend/pom.xml .
COPY SpringAI-Backend/src ./src
RUN ./mvnw package -DskipTests

# Stage 3: Combine Frontend and Backend
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
# Copy Spring Boot JAR
COPY --from=backend-build /app/target/*.jar app.jar
# Copy React build files
COPY --from=frontend-build /app/build ./static
# Set the entry point for the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
