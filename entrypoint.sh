#!/usr/bin/env bash

echo "Start compiling ..."
mvn clean package

echo "Starting db containers ..."
docker container start redis_cache postgres_db

echo "Stopping and removing old app container ..."
docker container stop url_shortener_app || true
docker container rm -f url_shortener_app || true

echo "Start docker compose for all containers ..."
docker compose up --build
