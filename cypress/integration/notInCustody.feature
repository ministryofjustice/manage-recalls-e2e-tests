Feature: Manage a recall for offender who are not in custody

  Scenario: Book a recall for a person not in custody
    Given Maria signs in
    And Maria enters their user details
    And Maria searches for the environment specific NOMS number
    And Maria clicks on the Book a recall link
    And Maria enters the licence name
    And Maria enters the pre-convictions name
    And Maria confirms the person is not in custody
    And Maria confirms the person has a last known address
    And Maria submits the date and email of the recall request received from probation service
    And Maria submits the sentence, offence and release details
    And Maria submits the police contact details
    And Maria submits any vulnerability, contraband and arrest issues for the offender
    And Maria submits the probation officer details
    And Maria does not upload any documents
    And Maria submits the reason for missing documents
    Then Maria can check their answers for the not in custody recall