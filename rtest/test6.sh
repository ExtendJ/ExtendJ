#!/usr/bin/env bash
set -eu
TEST_LEVEL=6 "$(dirname "$0")/test.sh" "$@"
