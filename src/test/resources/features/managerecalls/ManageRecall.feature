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
    Then Maria is on the recall request received page
    When Maria submits the date and time of the recall request received from probation service
    And Maria submits the latest release date and releasing prison details
    And Maria submits the police contact details
    And Maria uploads two documents
    Then Maria is on the Recall details page
    When Maria starts the assessment process for the recall
    Then Maria is on the decision on recall recommendation page
    When Maria confirms the recall length as 14 days
    Then the recall is authorised
    When Maria downloads revocation order which was generated
    Then a revocation order is downloaded

#  FIXME - this should use the ID of the recall that we just created
  Scenario: Can assess a recall
    Given Maria navigates to manage recall service
    When Maria logs in
    And Maria clicks To Do
    Then Maria is on the ToDo Recalls page
    When Maria clicks on the first View link to view a recall
    Then Maria is on the Recall details page