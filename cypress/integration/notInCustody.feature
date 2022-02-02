Feature: Not in custody

  Scenario: Book and assess a recall
    Given Maria signs in
    And Maria searches for the environment specific NOMS number
    And Maria clicks on the Book a recall link
    And Maria enters the licence name
    And Maria enters the pre-convictions name
    And Maria confirms the person is not in custody
    And Maria confirms the person has a last known address
    And Maria looks up an address by postcode
    And Maria types an address
    And Maria can see the addresses listed
    And Maria submits the date and email of the recall request received from probation service
    And Maria submits the sentence, offence and release details
    And Maria submits the police contact details
    And Maria submits any vulnerability, contraband and arrest issues for the offender
    And Maria submits the probation officer details
    And Maria does not upload any documents
    And Maria submits the reason for missing documents
    And Maria can check their answers for the not in custody recall
    And Maria deletes one of the last known addresses
    Then Maria completes the booking
    ### Assess the recall
    When Maria changes their caseworker band to 4+
    And Maria begins to assess the recall that they have just booked
    And Maria starts the assessment process for the recall
    And Maria confirms the recall length of 28 days
    And Maria submits the licence breach details
    And Maria confirms the person is not in custody
    And Maria opens the not in custody recall notification

