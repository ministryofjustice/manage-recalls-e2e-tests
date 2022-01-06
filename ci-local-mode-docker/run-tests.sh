#!/bin/bash

set -euo pipefail

function wait_for() {
  echo "Waiting for ${1} to be available..."
  until $(curl --output /dev/null --silent --head --fail ${2}); do
    echo '.'
    sleep 5
  done
}

manage_recalls_ui_url=http://manage-recalls-ui:3000/ping
hmpps_auth_url=http://hmpps-auth:8080/auth/health

wait_for "manage-recalls-ui" "${manage_recalls_ui_url}"
wait_for "hmpps-auth" "${hmpps_auth_url}"

sleep 2

cypress run \
  --config baseUrl=${manage_recalls_ui_url} \
  --browser chrome \
  --record \
  --key e883dffd-b644-4431-989d-0181ae34d0e6
