Feature: Manage Recalls

  Scenario: Logging into service
    Given Clive navigates to manage recall service
    When Clive logs in
    Then Clive can see the start page
