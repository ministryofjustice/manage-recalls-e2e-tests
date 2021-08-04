Feature: Manage Recalls

  Scenario: Can create then assess a recall
    Given Maria navigates to manage recall service
    When Maria logs in
    And Maria clicks Find a person
    Then Maria is on the Find a person page
    When Maria enters the NOMIS number A1234AA
    And Maria clicks Search
    When Maria clicks on the View link
    Then Maria is on the Person profile page
    And Maria clicks on the Create recall button
    Then Maria continues from the Book a recall page
    And Maria recommends a 14 day recall
    And Maria uploads two documents
    Then Maria is on the Assess Recall page
    And Maria confirms the recall length as 14 days
    And Maria downloads the documents
    When Maria clicks on the download revocation order link
    Then a revocation order is downloaded

#  FIXME - this should use the ID of the recall that we just created
  Scenario: Can navigate to assess a recall from the To do list
    Given Maria navigates to manage recall service
    When Maria logs in
    And Maria clicks To Do
    Then Maria is on the ToDo Recalls page
    When Maria clicks on the first View link to assess a recall
    Then Maria is on the Assess Recall page
