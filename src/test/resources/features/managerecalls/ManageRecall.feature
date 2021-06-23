Feature: Manage Recalls

  Scenario: Logging into service
    Given Clive navigates to manage recall service
    When Clive logs in
    Then Clive is on the Find An Offender page

  Scenario: Search by NOMIS number
    Given Fred navigates to manage recall service
    When Fred logs in
    Then Fred is on the Find An Offender page
    When Fred enters the NOMIS number A1234AA
    And Fred clicks Search
    Then Fred sees a search result of '1 people found'
    And Fred sees person entries matching 'A1234AA Robert Larsen 02 Apr 1975'