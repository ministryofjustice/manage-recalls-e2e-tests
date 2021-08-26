Feature: Manage Recalls

  Scenario: Book and Assess a recall
    Given Maria navigates to manage recall service
    When Maria logs in
    And Maria clicks Find a person
    When Maria enters the NOMIS number A1234AA
    And Maria clicks Search
    ### Book a recall
    When Maria clicks on the Book a recall link
    When Maria submits the date and time of the recall request received from probation service
    And Maria submits the sentence, offence and release details
    And Maria submits the police contact details
    And Maria submits any vulnerability and contraband related details for the offender
    And Maria submits the probation officer details
    And Maria uploads two documents
    Then Maria sees confirmation that the new recall was booked
    ### Assess the recall
    When Maria navigates to the 'To do' list
    When Maria clicks on the Assess link for the recall that they have just booked
    And Maria is able to see the details submitted earlier
    And Maria downloads the documents
    When Maria starts the assessment process for the recall
    When Maria confirms the recall length of 28 days
    And Maria submits the licence breach details
    And Maria submits the current prison details
    Then Maria can see that the recall is authorised
    And Maria downloads the revocation order
    ### confirm the details captured during the recall assessment journey are displayed
    When Maria navigates to the 'To do' list
    When Maria clicks on the Assess link for the recall that they have just booked
    Then Maria is able to see the details submitted earlier during assessment