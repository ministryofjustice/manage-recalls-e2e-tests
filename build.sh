#!/bin/bash

set -euo pipefail

readonly SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

./start-local-services.sh

echo "Running e2e tests..."
npm run cypress:ci

./stop-local-services.sh

