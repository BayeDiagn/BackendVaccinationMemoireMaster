# syntax=docker/dockerfile:1

# ── Étape 1 : compilation (toujours sur l'architecture du builder, le jar est portable)
FROM --platform=$BUILDPLATFORM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B -q package -DskipTests \
 && cp target/*.jar app.jar

# ── Étape 2 : image d'exécution légère (JRE seul, utilisateur non-root)
FROM eclipse-temurin:17-jre
WORKDIR /app
RUN groupadd --system spring && useradd --system --gid spring spring \
 && chown spring:spring /app
COPY --from=build --chown=spring:spring /build/app.jar app.jar
USER spring
EXPOSE 8087
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+ExitOnOutOfMemoryError"
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
