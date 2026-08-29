#!/usr/bin/env bash
set -eu
TEST_LEVEL=8 "$(dirname "$0")/test.sh" "$@"
