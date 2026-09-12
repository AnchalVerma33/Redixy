#!/bin/sh
#
# Builds and runs Redixy locally.
# Usage: ./run.sh [port]

set -e

cd "$(dirname "$0")"
mvn -q -B package
exec java --enable-preview -jar target/redixy.jar "$@"
