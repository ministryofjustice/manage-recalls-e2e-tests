Feature: Manage Recalls

  Scenario: Can create recall
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
    Then Maria is on the Recall details page
    And Maria sees the recall length is 14 days
    And Maria downloads the documents
    And Maria clicks Assess this recall
    And Maria agrees with the recommended recall length of 14 days
    Then Maria is on the assess recall confirmation page
    And Maria downloads the revocation order


#  FIXME - this should use the ID of the recall that we just created
  Scenario: Can assess a recall
    Given Maria navigates to manage recall service
    When Maria logs in
    And Maria clicks To Do
    Then Maria is on the ToDo Recalls page
    When Maria clicks on the first View link to view a recall
    Then Maria is on the Recall details page