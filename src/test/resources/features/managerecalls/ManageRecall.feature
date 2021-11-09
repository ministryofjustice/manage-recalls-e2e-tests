Feature: Manage Recalls

  Scenario: Book and Assess a recall
    Given Maria navigates to manage recall service
    And Maria logs in
    And Maria enters her user details
    When Maria searches for the environment specific NOMS number
    ### Book a recall
    And Maria clicks on the Book a recall link
    And Maria enters the pre-convictions name
    And Maria submits the date and email of the recall request received from probation service
    And Maria submits the sentence, offence and release details
    And Maria submits the police contact details
    And Maria submits any vulnerability and contraband related details for the offender
    And Maria submits the probation officer details
    And Maria uploads some documents
    And Maria can check their answers
    And Maria opens the documents
    # Then Maria can see the documents are uploaded
    Then Maria sees confirmation that the new recall was booked
    ### Assess the recall
    When Maria begins to assess the recall that they have just booked
    When Maria starts the assessment process for the recall
    When Maria confirms the recall length of 28 days
    And Maria submits the licence breach details
    And Maria submits the current prison details
    And Maria opens the recall notification
    And Maria records the issuance of recall notification
    Then Maria can see that the recall is assessed
    And Maria can see that they are unassigned from the recall
    ### confirm the details captured during the recall assessment journey are displayed
    When Maria navigates to view the details captured during assessment
    Then Maria is able to see the details captured during assessment
    And Maria is able to see the documents uploaded during booking
    ### create a dossier
    When Maria navigates to the recall to create a dossier
    Then Maria is able to see the recall information before creating a dossier
    And Maria submits the information for the prison letter
    And Maria has checked and created the reasons for recall document
    And Maria can open the dossier and letter
    And Maria has reviewed the dossier
    And Maria records that the dossier was emailed
    Then Maria gets a confirmation that the dossier creation is complete
    And Maria can see that they are unassigned from the recall after dossier creation is complete
    ### confirm the details captured during the dossier creation journey are displayed
    And Maria navigates to view the details captured during dossier creation
    Then Maria is able to see the details captured during dossier creation
    And Maria can download the dossier email