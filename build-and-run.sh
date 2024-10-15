#!/bin/sh

gradle build;
docker compose up -V --wait;
MICRONAUT_ENVIRONMENTS=local java -jar build/libs/tournament-0.1-all-optimized.jar
