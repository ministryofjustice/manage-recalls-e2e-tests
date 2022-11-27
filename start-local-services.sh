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

docker compose build
docker compose -f $LOCAL_DOCKER_COMPOSE_FILE pull redis gotenberg hmpps-auth fake-prisoner-offender-search-api fake-prison-api fake-prison-register-api fake-court-register-api fake-os-places-api postgres minio
docker compose -f $LOCAL_DOCKER_COMPOSE_FILE up redis gotenberg hmpps-auth fake-prisoner-offender-search-api fake-prison-api fake-prison-register-api fake-court-register-api fake-os-places-api postgres minio --remove-orphans -d

npx kill-port 3000 3001 8080

pushd ${MANAGE_RECALLS_UI_DIR}
npm run clean
npm run build
npm run start:e2e >>"${MANAGE_RECALLS_UI_LOG_FILE}" 2>&1 &
popd

pushd ${MANAGE_RECALLS_API_DIR}
SPRING_PROFILES_ACTIVE=dev RETURNTOCUSTODY_UPDATETHRESHOLDMINUTES=5 ./gradlew bootRun >>"${MANAGE_RECALLS_API_LOG_FILE}" 2>&1 &
popd

function wait_for {
  printf "\n\nWaiting for ${2} to be ready."
  until curl -s --fail "${1}"; do
    printf "."
    sleep 2
  done
}

wait_for "http://localhost:9090/auth/health/ping" "hmpps-auth"
wait_for "http://localhost:8081/health/ping" "${MANAGE_RECALLS_API_NAME}"
wait_for "http://localhost:3000/ping" "${MANAGE_RECALLS_UI_NAME}"

echo "Logs can be found by running:"
echo "  less ${MANAGE_RECALLS_API_LOG_FILE}"
echo "  less ${MANAGE_RECALLS_UI_LOG_FILE}"
