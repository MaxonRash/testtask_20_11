#!/bin/bash
docker-compose down

./mvnw clean
./mvnw package

docker-compose up --build -d