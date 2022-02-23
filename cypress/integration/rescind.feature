Feature: Rescind recall

  Background:
    Given Maria signs in
    And Maria enters their user details
    And Maria searches for the environment specific NOMS number
    And Maria clicks on the Book a recall link
    And Maria enters the licence name

  Scenario: Rescind a recall
    And Maria rescinds the recall
    Then Maria checks that the rescind has been requested
    And Maria updates the rescind
    Then Maria checks that the rescind decision has been made