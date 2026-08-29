#!/usr/bin/env bash
set -eu
TEST_LEVEL=5 "$(dirname "$0")/test.sh" "$@"
