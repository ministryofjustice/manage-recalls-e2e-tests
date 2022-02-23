#!/usr/bin/env bash

set -euo pipefail

function total_nodes {
  cat "${RESULTS_DIR}/TOTAL_NODES"
}

function exit_code_for_node {
  cat "${RESULTS_DIR}/E2E_RESULT_${1}"
}

function overall_exit_code {
  count=0
  exit_code=0

  while [ "${count}" -lt "$(total_nodes)" ]; do
    node_exit_code=$(exit_code_for_node ${count})

    if [ "${node_exit_code}" -gt 0 ]; then
      exit_code=1
    fi

    ((count += 1))
  done

  echo ${exit_code}
}

if [[ "${BASH_SOURCE[0]}" == "${0}" ]]; then
  overall_exit_code
fi
