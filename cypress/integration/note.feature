Feature: Add a note to a recall

  Background:
    Given Maria signs in
    And Maria enters their user details
    And Maria searches for an offender
    And Maria clicks on the Book a recall link
    And Maria selects the licence name

  Scenario: Add a note
    And Maria adds a note to the recall
    Then Maria checks that the note details have been recorded
