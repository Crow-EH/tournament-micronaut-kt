# Tournament API

## Requirements
* Java 21
* Gradle 8 (or use the provided gradlew scripts)
* Docker and docker compose

## Commands
* Single command to build + (re)start postgres + run: `./build-and-run.sh`
* Build and run with Gradle: `gradle run`
* Build with gradle and run with java: `gradle build` then `java -jar build/libs/tournament-0.1-all-optimized.jar`
