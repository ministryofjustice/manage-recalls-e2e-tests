#!/bin/bash

set -xeuo pipefail

node_version=$(jq -r '.engines.node' <package.json)
nvm install $node_version
nvm alias default $node_version
node -v

npm install -g npm@$(jq -r '.engines.npm' <package.json)
npm -v
