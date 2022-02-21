Feature: Stop recall

  Background:
    Given Maria signs in
    And Maria enters their user details
    And Maria searches for the environment specific NOMS number
    And Maria clicks on the Book a recall link
    And Maria enters the licence name

  Scenario: Stop a recall
    And Maria stops the recall
    Then Maria checks that the stop decision has been recorded
