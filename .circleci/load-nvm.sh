#!/bin/bash

set -euo pipefail

node_version=$(jq -r '.engines.node' <package.json)
nvm use $node_version
