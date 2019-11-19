#!/bin/bash

RED_COLOR='\033[0;31m'
BLUE_COLOR='\033[0;34m'
GREEN_COLOR='\033[0;32m'
NO_COLOR='\033[0m'
BOLD=$(tput bold)
NORMAL=$(tput sgr0)

if ! [ -x "$(command -v docker)" ]; then
  echo "${RED_COLOR}${BOLD}Error: docker is not installed.${NO_COLOR}${NORMAL}" >&2
  exit 1
fi

PROJECT_VERSION=$(./mvnw -q \
    -Dexec.executable=echo \
    -Dexec.args='${project.version}' \
    --non-recursive \
    exec:exec)

echo "${BLUE_COLOR}${BOLD}Building docker image from Dockerfile...${NO_COLOR}${NORMAL}"
docker build -t maccommerce/auth-service:latest -t maccommerce/auth-service:"$PROJECT_VERSION" .

echo "${BLUE_COLOR}${BOLD}Pushing docker image to Docker Hub...${NO_COLOR}${NORMAL}"
docker push maccommerce/auth-service:latest
docker push maccommerce/auth-service:"$PROJECT_VERSION"

echo "${GREEN_COLOR}${BOLD}Done!${NO_COLOR}${NORMAL}"