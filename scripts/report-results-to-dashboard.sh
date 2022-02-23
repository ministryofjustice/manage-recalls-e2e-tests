#!/usr/bin/env bash

set -euo pipefail

function usage {
  echo "
./$(basename $0) [option]
Options:
    -h --> show usage
    -e --> environment (REQUIRED) - allowed values: 'dev', 'preprod' or 'prod'
    -s --> successful (REQUIRED) - allowed values: 'true' or 'false'
  "
}

# get cli options
while getopts :e:s:h opt; do
  case ${opt} in
  e) ENV=${OPTARG} ;;
  s) SUCCESSFUL=${OPTARG} ;;
  h)
    usage
    exit
    ;;
  \?)
    echo "Unknown option: -${OPTARG}" >&2
    exit 1
    ;;
  :)
    echo "Missing option argument for -${OPTARG}" >&2
    exit 1
    ;;
  *)
    echo "Unimplemented option: -${OPTARG}" >&2
    exit 1
    ;;
  esac
done

# check for the ENV variables
set +u
if [[ ! "${ENV}" =~ ^(dev|preprod|prod)$ ]]; then
  usage
  exit 1
fi

if [[ ! "${SUCCESSFUL}" =~ ^(true|false)$ ]]; then
  usage
  exit 1
fi
set -u

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
UI_INFO=$(curl -s "${MANAGE_RECALLS_UI_URL[${ENV}]}")
UI_VERSION=$(echo "${UI_INFO}" | jq -r '.build.buildNumber')
UI_BUILD_URL=$(echo "${UI_INFO}" | jq -r '.build.buildUrl')
API_INFO=$(curl -s "${MANAGE_RECALLS_API_URL[${ENV}]}")
API_VERSION=$(echo "${API_INFO}" | jq -r '.version')
API_BUILD_URL=$(echo "${API_INFO}" | jq -r '.buildUrl')

REQ_BODY=$(
  jq -cM . <<-EOF
{
  "auth_token": "${DASHBOARDS_AUTH_TOKEN}",
  "e2e_build_url": "${CIRCLE_BUILD_URL}",
  "environment": "${ENV}",
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
  -d "${REQ_BODY}" \
  "${DASHBOARDS_URL}"
