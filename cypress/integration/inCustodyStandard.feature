Feature: In custody (Standard)

  Background:
    Given Maria signs in
    And Maria enters their user details

  Scenario: Book and assess a recall
    Given Maria searches for an offender with no middle name
    ### Book a recall
    When Maria clicks on the Book a recall link
    And Maria selects the pre-convictions name
    And Maria confirms the person is in custody
    And Maria sets the recall type as Standard
    And Maria submits the date and email of the recall request received from probation service
    And Maria submits the sentence, offence and release details
    And Maria submits the police contact details
    And Maria submits any vulnerability and contraband related details for the offender
    And Maria submits the probation officer details
    And Maria uploads all required documents
    Then Maria confirms the standard recall type on Check your answers
    Then Maria completes the booking
    ### Assess the recall
    And Maria begins to assess the recall that they have just booked
    And Maria starts the assessment process for the recall
    And Maria confirms the standard recall
    And Maria submits the licence breach details
    And Maria submits the current prison details
    And Maria opens the in custody recall notification
    And Maria records the issuance of recall notification
    Then Maria can see that the recall is assessed
    And Maria can see that they are unassigned from the recall
    ### create a dossier
    When Maria navigates to the recall to create a dossier
    Then Maria is able to see the recall information before creating a dossier
    And Maria submits the information for the prison letter
    And Maria has checked and created the reasons for recall document
    And Maria can open the dossier and letter to prison
    And Maria has reviewed the dossier and letter to prison
    And Maria records that the dossier was emailed
    Then Maria sees confirmation that the dossier creation is complete
    ### confirm the details captured during the dossier creation journey are displayed
    And Maria can see that the recall appears on the Awaiting part B tab
    And Maria confirms the details captured during dossier creation
    ### Upload part B
    And Maria uploads the part B report
    And Maria views the part B details
