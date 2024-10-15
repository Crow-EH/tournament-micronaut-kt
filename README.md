# Tournament API

A simple API to handle players' scores on a single active tournament.

All run profiles (test and locale) use both a postgres container and flyway for migration.

A Swagger UI documentation is available bellow.

## Requirements
* Java 21
* Gradle 8 (or use the provided gradlew/gradlew.bat scripts)
* Docker and docker compose for postgres during tests and local runs

## Commands
* Test: `gradle test`. During `test` (and `build`, that includes `test`), a local postgres docker container will be started by Testcontainers.
* Single script to build + test + (re)start postgres + run with non-test profile: `./build-and-run.sh`
* Build and run with Gradle with test profile (postgres started automatically): `gradle run`
* Manual build with gradle without test and run with java: `gradle assemble` then `MICRONAUT_ENVIRONMENTS=local java -jar build/libs/tournament-0.1-all-optimized.jar`
* Lint and format with ktlint: `gradle spotlessApply`
* Check only: `gradle spotlessCheck`

## Resources
* OpenAPI: http://localhost:8080/api/v1/swagger/tournament-api-0.1.yml
* Swagger UI: http://localhost:8080/api/v1/swagger-ui
* Make sure to pass the correct `Content-Type` header, otherwise you'll get a 415 !
* Example: `curl -v -X POST localhost:8080/api/v1/players --data '{ "nickname": "bob" }' -H 'Content-Type: application/json'`

## What's missing to release in production

### A better data model and API model (optional)
* We should have a Tournament table (1-N with Player). This would allow:
  * Data retention if we have to implement a history later or simply complete the API model with tournaments management
  * Multiple tournaments at the same time (but that would require an API modification too, adding /tournaments/{id} everywhere)
* To do it without modifying the API yet (a.k.a. one "hidden" active tournament at a time):
  * Tournament would have an id, a start date, and an end date
  * Player PK is now tournament id + nickname, instead of nickname. Meaning nickname is unique by tournament, not the whole Player table.
  * Tournament is considered active if end date is null or in the future
  * When creating a player and no tournament is active, create one
  * When checking if a player exists or fetching players, check if there is an active tournament first and use its id
  * When deleting all players, actually end the tournaments: If the previous rules are followed, it's technically a soft delete
* With a different API model also handling tournaments: a User entity holding the nickname, with Player only representing the participation of 1 User to 1 Tournament would be better.
  * Something like Tournament 1-N Player N-1 User
  * User can also be external and its info got from a JWT

### Better Security
* If we have many users participating to multiple tournaments, we should have reasonable rules to make sure only users from the tournament can invite others and interact with it
* Something simple like requiring a signed JWT and systematically checking if the user is already in the tournament (through a PLayer) could be enough
* The CORS and other standard security headers are not defined. They should be once the clients are known.

### Better quality and configuration
* More tests are needed, I only tested the happy path
* A CI is needed, otherwise the tests won't be run automatically
* A custom docker images could be better for the eventual deployment. A basic one is already provided with a Gradle plugin (with `gradle buildDocker`).
* We should have a deployment plan, and whatever the solution chosen (ecs, k8s, etc.) the project should have a better way to handle configuration (per env, hot reloaded, etc.) and secrets.
* Once done, a CD at least on the main branch on a dev env would be better to know if the application is actually still running on ISO envs
* Depending on the deployment plan and the scaling configuration, the very basic Flyway migration (at application startup) may not be the best. Having an external tool handling sync between blue/green env could be better, or any other way to ensure service continuity. 
* Automatic external tests on a real environment would be a plus.
* Measuring the coverage and scanning the project with Sonarqube would be a plus too.
* Check formatting in CI to avoid useless noise in reviews.

### Better monitoring and a look on the performance
* The logs should be collected and indexed somewhere, preferably with visualization and monitored aggregations (i.e. error rate, etc.). Something like an ELK or Datadog.
* An APM could be a plus to instrument eventual optimization projects / performance improvement tasks
* Request tracing, at least with a trace id/request id that's found in the logs is a big plus to ease searching through the logs when reproducing on ISO envs.
* There's probably some work to do on the find one ranked query, the use of a sub-select is a bit brutal.
* An automatic load test (gatling, k6, etc.), either in CI or a real ISO env could be a plus to avoid surprise big performance diffs in production.
