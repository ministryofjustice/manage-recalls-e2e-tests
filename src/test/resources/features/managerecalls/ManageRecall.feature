Feature: Manage Recalls

  Scenario: Can login into service
    Given Clive navigates to manage recall service
    When Clive logs in
    And Clive clicks Find someone
    Then Clive is on the Find An Offender page

  Scenario: Can find an offender, view offender profile and download a revocation order
    Given Fred navigates to manage recall service
    When Fred logs in
    And Fred clicks Find someone
    Then Fred is on the Find An Offender page
    When Fred enters the NOMIS number A1234AA
    And Fred clicks Search
    Then Fred sees a search result of '1 person found'
    And Fred sees a person entry with nomsNumber 'A1234AA' and non-empty name and DoB fields
    When Fred clicks on the View profile link
    Then Fred is on the Offender profile page
    And Fred clicks on the download revocation order link
# Revocation generation not working running locally
    Then a revocation order is downloaded

  Scenario: Can create a recall, see the To Do list and view the Assess Recall page
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
    And Maria sees at least one View link to assess a recall
    When Maria clicks on the first View link to assess a recall
    Then Maria is on the Assess Recall page
