Feature: Manage Recalls

  Scenario: Book and Assess a recall
    Given Maria navigates to manage recall service
    And Maria logs in
    When Maria searches for the environment specific NOMS number
    ### Book a recall
    And Maria clicks on the Book a recall link
    And Maria submits the date and time of the recall request received from probation service
    And Maria submits the sentence, offence and release details
    And Maria submits the police contact details
    And Maria submits any vulnerability and contraband related details for the offender
    And Maria submits the probation officer details
    And Maria uploads two documents
    Then Maria sees confirmation that the new recall was booked
    ### Assess the recall
    When Maria begins to assess the recall that they have just booked
    Then Maria is able to see the details captured during booking
    And Maria downloads the documents
    When Maria starts the assessment process for the recall
    When Maria confirms the recall length of 28 days
    And Maria submits the licence breach details
    And Maria submits the current prison details
    And Maria records the issuance of recall notification
    Then Maria can see that the recall is authorised
    And Maria downloads the revocation order
    ### confirm the details captured during the recall assessment journey are displayed
    When Maria begins to assess the recall that they have just booked
    Then Maria is able to see the details captured during assessment
    ### create a dossier
    When Maria navigates to the recall to create a dossier
    And Maria submits the information for the prison letter
    Then Maria can download the dossier
    When Maria has reviewed the dossier
    Then Maria gets a confirmation that the dossier creation is complete
    And Maria navigates to view the details captured during dossier creation
    Then Maria is able to see the details captured during dossier creation