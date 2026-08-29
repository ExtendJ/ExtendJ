#!/usr/bin/env bash
set -eu
TEST_LEVEL=11 "$(dirname "$0")/test.sh" "$@"
