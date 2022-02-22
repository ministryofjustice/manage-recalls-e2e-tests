Feature: Not in custody
  Background:
    Given Maria signs in
    And Maria enters their user details
    And Maria searches for the environment specific NOMS number
    And Maria clicks on the Book a recall link
    And Maria enters the licence name
    And Maria enters the pre-convictions name

  Scenario: Book and assess a recall
    When Maria confirms the person is not in custody
    And Maria confirms the person has a last known address
    And Maria looks up an address by postcode
    And Maria types an address
    And Maria can see the addresses listed
    And Maria submits the date and email of the recall request received from probation service
    And Maria submits the sentence, offence and release details
    And Maria submits the police contact details
    And Maria submits any vulnerability, contraband and arrest issues for the offender
    And Maria submits the probation officer details
    And Maria uploads some documents
    And Maria submits the reason for missing documents
    And Maria can check their answers for the not in custody recall
    And Maria deletes one of the last known addresses
    Then Maria completes the booking

    ### Assess the recall
    And Maria begins to assess the recall that they have just booked
    And Maria starts the assessment process for the recall
    And Maria confirms the recall length of 28 days
    And Maria submits the licence breach details
    And Maria confirms the person is not in custody
    And Maria opens the not in custody recall notification
    And Maria records the issuance of recall notification
    Then Maria can see that the recall is assessed
    And Maria can see that they are assigned to the recall on the Not in custody tab
    And Maria adds a warrant reference number
    And Maria confirms the person is awaiting return to custody
    And Maria adds a returned to custody date

    ### create a dossier
    When Maria navigates to the recall to create a dossier
    Then Maria is able to see the recall information before creating a dossier
    And Maria submits the current prison details
    And Maria uploads the email sent to New Scotland Yard
    And Maria submits the information for the prison letter
    And Maria has checked and created the reasons for recall document
    And Maria can open the dossier and letter
    And Maria has reviewed the dossier
    And Maria records that the dossier was emailed
    Then Maria sees confirmation that the dossier creation is complete
    ### confirm the details captured during the dossier creation journey are displayed
    And Maria confirms recall information for the "not in custody" recall