#!/bin/bash

set -euo pipefail

function wait_for() {
  echo "Waiting for ${1} to be available..."
  until $(curl --output /dev/null --silent --head --fail ${2}); do
    echo '.'
    sleep 5
  done
}

function wait_for_redis() {
  echo "Waiting for redis to be available..."
  until [ "$(redis-cli -h redis -p 6379 ping)" == "PONG" ]; do
    echo '.'
    sleep 5
  done
}

wait_for_redis
wait_for "manage-recalls-ui" "http://manage-recalls-ui:3000/ping"
wait_for "hmpps-auth" "http://hmpps-auth:8080/auth/health"

sleep 2

export DEBUG="cypress:network:*"

cypress run \
  --config baseUrl=http://manage-recalls-ui:3000 \
  --browser chrome \
  --record \
  --key e883dffd-b644-4431-989d-0181ae34d0e6
