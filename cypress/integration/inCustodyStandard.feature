Feature: In custody (Standard)

  Background:
    Given Maria signs in
    And Maria enters their user details

  Scenario: Book and assess a recall
    Given Maria searches for the environment specific NOMS number
    ### Book a recall
    When Maria clicks on the Book a recall link
    And Maria enters the licence name
    And Maria enters the pre-convictions name
    And Maria confirms the person is in custody
    And Maria sets the recall type as Standard
    And Maria submits the date and email of the recall request received from probation service
    And Maria submits the sentence, offence and release details
    And Maria submits the police contact details
    And Maria submits any vulnerability and contraband related details for the offender
    And Maria submits the probation officer details
    And Maria uploads some documents
    And Maria submits the reason for missing documents
    Then Maria confirms the standard recall type on Check your answers