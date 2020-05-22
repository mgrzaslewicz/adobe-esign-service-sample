#!/bin/bash
# turn on debug mode
set -x

SERVICE_NAME=adobe-esign-service-sample
ALREADY_RUNNING_CONTAINER="$(docker ps -a -q  --filter ancestor=$SERVICE_NAME)"

buildJar() {
    echo "Building docker container"
    docker build -f Dockerfile-build-and-start -t $SERVICE_NAME .
}

removeOldContainer() {
    if [ -z "$ALREADY_RUNNING_CONTAINER" ]
    then
        echo "No previous containers running found"
    else
        echo "Removing old containers"
        docker stop "${ALREADY_RUNNING_CONTAINER}"
        docker rm "${ALREADY_RUNNING_CONTAINER}"
    fi
}

startContainer() {
    echo "Starting container"
    docker run --name ${SERVICE_NAME} -d \
    -p 80:80 \
    --memory=120m \
    --restart=always \
    ${SERVICE_NAME}
}

buildJar && removeOldContainer && startContainer
