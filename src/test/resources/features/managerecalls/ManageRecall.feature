Feature: Manage Recalls

  Scenario: Can create a recall, see the To Do list and view the Assess Recall page and download a revocation order
    Given Maria navigates to manage recall service
    When Maria logs in
    And Maria clicks Find someone
    Then Maria is on the Find An Offender page
    When Maria enters the NOMIS number A1234AA
    And Maria clicks Search
    When Maria clicks on the View profile link
    Then Maria is on the Offender profile page
    And Maria clicks on the create recall button
    And Maria clicks To Do
    Then Maria is on the ToDo Recalls page
    #FIXME - this should use the ID of the recall that we just created
    And Maria sees at least one View link to assess a recall
    When Maria clicks on the first View link to assess a recall
    Then Maria is on the Assess Recall page
    When Maria clicks on the download revocation order link
    Then a revocation order is downloaded
