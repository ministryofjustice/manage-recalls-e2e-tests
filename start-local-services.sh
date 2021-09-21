#!/bin/bash

set -euo pipefail

readonly SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
readonly MANAGE_RECALLS_UI_NAME=manage-recalls-ui
readonly MANAGE_RECALLS_API_NAME=manage-recalls-api
readonly MANAGE_RECALLS_UI_DIR=${SCRIPT_DIR}/../${MANAGE_RECALLS_UI_NAME}
readonly MANAGE_RECALLS_API_DIR=${SCRIPT_DIR}/../${MANAGE_RECALLS_API_NAME}
readonly MANAGE_RECALLS_UI_LOG_FILE="/tmp/${MANAGE_RECALLS_UI_NAME}-e2e.log"
readonly MANAGE_RECALLS_API_LOG_FILE="/tmp/${MANAGE_RECALLS_API_NAME}-e2e.log"
readonly LOCAL_DOCKER_COMPOSE_FILE=docker-compose.yml

docker compose -f $LOCAL_DOCKER_COMPOSE_FILE pull redis gotenberg hmpps-auth fake-prisoner-offender-search-api fake-prison-register-api postgres localstack
docker compose -f $LOCAL_DOCKER_COMPOSE_FILE up redis gotenberg hmpps-auth fake-prisoner-offender-search-api fake-prison-register-api postgres localstack --remove-orphans -d

npx kill-port 3000 8080

pushd ${MANAGE_RECALLS_UI_DIR}
npm run clean
npm run build
npm run start:e2e >> "${MANAGE_RECALLS_UI_LOG_FILE}" 2>&1 &
popd

pushd ${MANAGE_RECALLS_API_DIR}
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun >> "${MANAGE_RECALLS_API_LOG_FILE}" 2>&1 &
popd

printf "\nChecking hmpps-auth is running..."
docker run --network container:hmpps-auth-e2e \
    appropriate/curl -s -4 --retry 120 --retry-delay 1 --retry-connrefused http://localhost:8080/auth/health/ping

printf "\nChecking ${MANAGE_RECALLS_API_NAME} is running..."
curl -s -4 --retry 20 --retry-delay 1 --retry-connrefused http://localhost:8080/health/ping

printf "\nChecking ${MANAGE_RECALLS_UI_NAME} is running..."
curl -s -4 --retry 20 --retry-delay 1 --retry-connrefused http://localhost:3000/ping

echo "Logs can be found by running:"
echo "  less ${MANAGE_RECALLS_API_LOG_FILE}"
echo "  less ${MANAGE_RECALLS_UI_LOG_FILE}"