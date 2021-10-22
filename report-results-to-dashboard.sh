#!/usr/bin/env bash

set -euo pipefail

declare -A MANAGE_RECALLS_UI_URL=(
  ["dev"]="https://manage-recalls-dev.hmpps.service.justice.gov.uk/health"
  ["preprod"]="https://manage-recalls-preprod.hmpps.service.justice.gov.uk/health"
  ["prod"]="https://manage-recalls.hmpps.service.justice.gov.uk/health"
)

declare -A MANAGE_RECALLS_API_URL=(
  ["dev"]="https://manage-recalls-api-dev.hmpps.service.justice.gov.uk/health"
  ["preprod"]="https://manage-recalls-api-preprod.hmpps.service.justice.gov.uk/health"
  ["prod"]="https://manage-recalls-api.hmpps.service.justice.gov.uk/health"
)

DASHBOARDS_URL=https://ppud-replacement-dashboards.hmpps.service.justice.gov.uk/data/manage-recalls-e2e-result
UI_INFO=$(curl -s "${MANAGE_RECALLS_UI_URL[${ENVIRONMENT}]}")
UI_VERSION=$(echo "${UI_INFO}" | jq -r '.build.buildNumber')
UI_BUILD_URL=$(echo "${UI_INFO}" | jq -r '.build.buildUrl')
API_INFO=$(curl -s "${MANAGE_RECALLS_API_URL[${ENVIRONMENT}]}")
API_VERSION=$(echo "${API_INFO}" | jq -r '.components.healthInfo.details.version')
API_BUILD_URL=$(echo "${API_INFO}" | jq -r '.components.healthInfo.details.build_url')

SUCCESSFUL=false
if [ ${E2E_RESULT} -eq 0 ]; then
  SUCCESSFUL=true
fi

req_body=$(
  jq -cM <<-EOF
{
  "auth_token": "${DASHBOARDS_AUTH_TOKEN}",
  "e2e_build_url": "${CIRCLE_BUILD_URL}",
  "environment": "${ENVIRONMENT}",
  "successful": ${SUCCESSFUL},
  "ui_version": "${UI_VERSION}",
  "ui_build_url": "${UI_BUILD_URL}",
  "api_version": "${API_VERSION}",
  "api_build_url": "${API_BUILD_URL}"
}
EOF
)

curl \
  -X POST \
  -H 'Content-Type: application/json' \
  -d "${req_body}" \
  "${DASHBOARDS_URL}"
