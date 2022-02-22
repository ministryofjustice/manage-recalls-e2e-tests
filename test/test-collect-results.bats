#!/usr/bin/env bats

function setup() {
  load 'test_helper/bats-support/load'
  load 'test_helper/bats-assert/load'

  source 'scripts/collect-results.sh'

  export RESULTS_DIR=workspace/env-output
  mkdir -p ${RESULTS_DIR}
  echo 2 >${RESULTS_DIR}/TOTAL_NODES
  echo 0 >${RESULTS_DIR}/E2E_RESULT_0
  echo 0 >${RESULTS_DIR}/E2E_RESULT_1
}

@test "can run our script" {
  ./scripts/collect-results.sh
}

@test "total_nodes: Returns the correct value" {
  echo 2 >${RESULTS_DIR}/TOTAL_NODES
  run total_nodes
  assert_output 2

  echo 15 >${RESULTS_DIR}/TOTAL_NODES
  run total_nodes
  assert_output 15
}

@test "exit_code_for_node: Returns the correct file" {
  echo 2 >${RESULTS_DIR}/E2E_RESULT_0
  run exit_code_for_node 0
  assert_output 2

  echo 15 >${RESULTS_DIR}/E2E_RESULT_1
  run exit_code_for_node 1
  assert_output 15
}

@test "overall_exit_code: When all PASSED returns 0" {
  echo 2 >${RESULTS_DIR}/TOTAL_NODES
  echo 0 >${RESULTS_DIR}/E2E_RESULT_0
  echo 0 >${RESULTS_DIR}/E2E_RESULT_1

  run overall_exit_code
  assert_output "0"
}

@test "overall_exit_code: When any FAILS returns 1" {
  echo 2 >${RESULTS_DIR}/TOTAL_NODES
  echo 1 >${RESULTS_DIR}/E2E_RESULT_0
  echo 0 >${RESULTS_DIR}/E2E_RESULT_1

  run overall_exit_code
  assert_output "1"

  echo 2 >${RESULTS_DIR}/TOTAL_NODES
  echo 0 >${RESULTS_DIR}/E2E_RESULT_0
  echo 1 >${RESULTS_DIR}/E2E_RESULT_1

  run overall_exit_code
  assert_output "1"

  echo 3 >${RESULTS_DIR}/TOTAL_NODES
  echo 0 >${RESULTS_DIR}/E2E_RESULT_0
  echo 1 >${RESULTS_DIR}/E2E_RESULT_1
  echo 0 >${RESULTS_DIR}/E2E_RESULT_2

  run overall_exit_code
  assert_output "1"
}
