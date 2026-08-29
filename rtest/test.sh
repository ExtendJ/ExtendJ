#!/bin/bash

set -eu

cd "$(dirname "$0")"

: "${TEST_LEVEL:=${JDK_VERSION:-8}}"

IMAGE_NAME="extendj-test${TEST_LEVEL}-run"

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

# Build the test image (a cached no-op when nothing changed).
USER_ID="$(id -u)" GROUP_ID="$(id -g)" docker buildx bake "test${TEST_LEVEL}"

# Build the compiler under test
(cd ..; ./gradlew :java${TEST_LEVEL}:jar)
if [ ! -f ../java${TEST_LEVEL}/extendj.jar ]; then
  echo "Missing ExtendJ jar file."
  exit 1
fi

mkdir -p reports

# Only allocate a TTY when attached to one, so tests can run headless.
TTY_FLAGS=""
if [ -t 0 ] && [ -t 1 ]; then
  TTY_FLAGS="-it"
fi

# Extra arguments are forwarded to Ant, e.g. ./test.sh -Dtest=generics/wildcard_01p
docker run --rm $TTY_FLAGS \
  --user "$(id -u):$(id -g)" \
  -v "../java${TEST_LEVEL}/extendj.jar:/test/rtest/extendj.jar:ro" \
  -v "$PWD/reports:/test/rtest/reports" \
  "$IMAGE_NAME" "$@"
