#!/bin/bash

set -euo pipefail

readonly SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

npm install

./start-local-services.sh

echo "Running e2e tests (headless)..."
npm run cypress:ci

#echo "Running e2e tests (headful)..."
#npm run cypress

./stop-local-services.sh

