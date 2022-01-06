#!/bin/bash

set -xeuo pipefail

docker-compose -f docker-compose.yml -f docker-compose.ci.yml build
docker-compose -f docker-compose.yml -f docker-compose.ci.yml pull
docker-compose -f docker-compose.yml -f docker-compose.ci.yml up --exit-code-from manage-recalls-e2e-test-runner manage-recalls-e2e-test-runner || {
  export exit_code=1
}

rm -rf logs && mkdir logs
docker logs manage-recalls-ui-e2e >logs/manage-recalls-ui.log
docker logs manage-recalls-api-e2e >logs/manage-recalls-api.log
docker logs localstack_manage-recalls-e2e >logs/localstack_manage-recalls-e2e.log

rm -rf screenshots
docker cp manage-recalls-e2e-test-runner:/e2e/cypress/screenshots/ .

exit $exit_code
