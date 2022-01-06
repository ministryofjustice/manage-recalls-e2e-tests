#!/bin/bash

set -euo pipefail

function wait_for() {
  echo "Waiting for ${1} to be available..."
  until $(curl --output /dev/null --silent --head --fail ${2}); do
    echo '.'
    sleep 5
  done
}

wait_for "manage-recalls-ui" "http://manage-recalls-ui:3000/ping"
wait_for "hmpps-auth" "http://hmpps-auth:8080/auth/health"

sleep 2

cypress run \
  --config baseUrl=http://manage-recalls-ui:3000 \
  --browser chrome \
  --record \
  --key e883dffd-b644-4431-989d-0181ae34d0e6
