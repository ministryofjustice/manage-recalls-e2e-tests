Feature: Manage Recalls

  Scenario: Can create a recall
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
    And Maria continues from the Upload documents page
    Then Maria is on the Assess Recall page

  Scenario: Can assess a recall and download a revocation order
    Given Maria navigates to manage recall service
    When Maria logs in
    And Maria clicks To Do
    Then Maria is on the ToDo Recalls page
    When Maria clicks on the first View link to assess a recall
    Then Maria is on the Assess Recall page
    When Maria clicks on the download revocation order link
    Then a revocation order is downloaded
