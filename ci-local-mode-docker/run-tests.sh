#!/bin/bash

set -euo pipefail

manage_recalls_url=http://manage-recalls-ui:3000

echo "Waiting for manage-recalls-ui to be available..."
until $(curl --output /dev/null --silent --head --fail $manage_recalls_url); do
  printf '.'
  sleep 5
done

sleep 2

cypress run \
  --config baseUrl=$manage_recalls_url \
  --browser chrome \
  --key e883dffd-b644-4431-989d-0181ae34d0e6
