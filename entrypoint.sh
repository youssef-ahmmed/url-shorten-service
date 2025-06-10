#!/usr/bin/env bash

echo "Start compiling ..."
mvn clean package

echo "Starting db containers ..."
docker container start redis_cache postgres_db

echo "Removing old app container ..."
docker image rm -f url-shortener-app:latest

echo "Start docker compose for all containers ..."
docker compose up --build
