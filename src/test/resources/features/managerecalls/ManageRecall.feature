Feature: Manage Recalls

  Scenario: Book and Assess a recall
    Given Maria navigates to manage recall service
    When Maria logs in
    And Maria clicks Find a person
    Then Maria is on the Find a person page
    When Maria enters the NOMIS number A1234AA
    And Maria clicks Search
    When Maria clicks on the View link
    Then Maria is on the Person profile page
    And Maria clicks on the Create recall button
    Then Maria is on the recall request received page
    When Maria submits the date and time of the recall request received from probation service
    And Maria submits the latest release date and releasing prison details
    And Maria submits the police contact details

    And Maria uploads two documents
    ###
    # TODO at some point this will change to just show the confirmation screen with the recall id.
    Then Maria is on the Recall details page
    And Maria is able to see the details submitted earlier
    And Maria downloads the documents
    When Maria starts the assessment process for the recall
    Then Maria is on the decision on recall recommendation page
    #  FIXME - UI Bug https://dsdmoj.atlassian.net/browse/PUD-379
    When Maria confirms the recall length as 14 days
    Then Maria can see that the recall is authorised
    Then Maria downloads revocation order

    ### Assess flow
    When Maria navigates to the 'To do' list
    When Maria clicks on the View link for the booked recall
    Then Maria is on the Recall details page
    And Maria is able to see the details submitted earlier