#!/bin/bash

set -eu

CONTAINER_NAME="extendj-test8"
IMAGE_NAME="extendj-test8-run"
SHOULD_BUILD=false

if [[ "$(docker images -q $IMAGE_NAME 2> /dev/null)" == "" ]]; then
  echo "Image does not exist."
  SHOULD_BUILD=true
else
  DOCKERFILE_MOD_TIME=$(date -r Dockerfile +%s 2>/dev/null)
  IMAGE_CREATION_ISO=$(docker inspect --format='{{.Created}}' "$IMAGE_NAME" 2>/dev/null)
  IMAGE_CREATION_TIME=$(date -d "$IMAGE_CREATION_ISO" +%s 2>/dev/null)
  if [[ "$DOCKERFILE_MOD_TIME" -gt "$IMAGE_CREATION_TIME" ]]; then
    echo "Dockerfile is newer than the existing image."
    SHOULD_BUILD=true
  fi
fi

if [ "$SHOULD_BUILD" = true ]; then
  # Clean old container
  docker rm $CONTAINER_NAME || true

  echo "Building Docker image..."
  docker build \
    --ssh default \
    --build-arg USER_ID="$(id -u)" \
    --build-arg GROUP_ID="$(id -g)" \
    -t $IMAGE_NAME .
fi

# Run the container
if [ "$(docker ps -a -q -f name=^/${CONTAINER_NAME}$)" ]; then
  echo "Found existing container. Restarting..."
  docker start -ai "$CONTAINER_NAME"
else
  cp ../java8/extendj.jar . || { echo "Missing ExtendJ jar file."; exit 1; }
  docker run -it \
    --name "$CONTAINER_NAME" \
    --user "$(id -u):$(id -g)" \
    -v "$PWD:/test/regtest" \
    -v "$PWD/lib:/test/regtest/lib" \
    -v $SSH_AUTH_SOCK:$SSH_AUTH_SOCK \
    -e SSH_AUTH_SOCK=$SSH_AUTH_SOCK \
    $IMAGE_NAME
fi
