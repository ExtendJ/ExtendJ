#!/bin/bash

set -eu

: "${JDK_VERSION:=8}"

CONTAINER_NAME="extendj-test${JDK_VERSION}"
IMAGE_NAME="extendj-test${JDK_VERSION}-run"
SHOULD_BUILD=false

if [ ! -f "lib/junit-4.11.jar" ]; then
  (cd lib; curl -sSLO https://repo1.maven.org/maven2/junit/junit/4.11/junit-4.11.jar)
fi
if [ ! -f "lib/hamcrest-core-1.3.jar" ]; then
  (cd lib; curl -sSLO https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar)
fi
if [ ! -f "lib/ant-1.10.5.jar" ]; then
  (cd lib; curl -sSLO https://repo1.maven.org/maven2/org/apache/ant/ant/1.10.5/ant-1.10.5.jar)
fi
if [ ! -f "lib/ant-junit-1.10.5.jar" ]; then
  (cd lib; curl -sSLO https://repo1.maven.org/maven2/org/apache/ant/ant-junit/1.10.5/ant-junit-1.10.5.jar)
fi

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
    --build-arg JDK_VERSION="${JDK_VERSION}" \
    --build-arg USER_ID="$(id -u)" \
    --build-arg GROUP_ID="$(id -g)" \
    -t $IMAGE_NAME .
fi

# Run the container
if [ "$(docker ps -a -q -f name=^/${CONTAINER_NAME}$)" ]; then
  echo "Found existing container. Restarting..."
  docker start -ai "$CONTAINER_NAME"
else
  (cd ..; ./gradlew :java${JDK_VERSION}:jar)
  cp ../java${JDK_VERSION}/extendj.jar . || { echo "Missing ExtendJ jar file."; exit 1; }
  docker run -it \
    --name "$CONTAINER_NAME" \
    --user "$(id -u):$(id -g)" \
    -v "$PWD:/test/regtest" \
    -v $SSH_AUTH_SOCK:$SSH_AUTH_SOCK \
    -e SSH_AUTH_SOCK=$SSH_AUTH_SOCK \
    $IMAGE_NAME
fi
