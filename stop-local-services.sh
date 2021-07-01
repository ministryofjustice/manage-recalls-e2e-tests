#!/bin/bash

set -euo pipefail

readonly SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
readonly MANAGE_RECALLS_UI_NAME=manage-recalls-ui
readonly MANAGE_RECALLS_API_NAME=manage-recalls-api
readonly MANAGE_RECALLS_UI_DIR=${SCRIPT_DIR}/../${MANAGE_RECALLS_UI_NAME}
readonly DOCKER_COMPOSE_FILE=docker-compose.yml

echo "Stopping docker containers"
docker compose -f $DOCKER_COMPOSE_FILE stop

pushd ${MANAGE_RECALLS_UI_DIR}
echo "Stopping ${MANAGE_RECALLS_UI_NAME}"
npx kill-port 3000
echo "Stopping ${MANAGE_RECALLS_API_NAME}"
npx kill-port 8080
popd

