# ===============================
# STAGE 1 — BUILD AVEC MAVEN
# ===============================
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Dossier de travail dans le conteneur
WORKDIR /app

# Copier les fichiers nécessaires au build
COPY pom.xml .
COPY src ./src

# Compiler le projet Spring Boot (sans tests pour aller plus vite)
RUN mvn clean package -DskipTests


# ===============================
# STAGE 2 — RUN AVEC JAVA
# ===============================
FROM eclipse-temurin:17-jdk-alpine

# Dossier de travail
WORKDIR /app

# Copier le JAR généré depuis le stage build
COPY --from=build /app/target/*.jar app.jar

# Port exposé par l’application Spring Boot
EXPOSE 8082

# Profil Spring actif (prod)
ENV SPRING_PROFILES_ACTIVE=prod

# Lancer l’application
ENTRYPOINT ["java", "-jar", "app.jar"]
