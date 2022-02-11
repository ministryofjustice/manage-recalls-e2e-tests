Feature: Rescinding a recall

  Scenario: Rescinds an in custody recall
    Given Maria signs in
    And Maria enters their user details
    And Maria searches for the environment specific NOMS number

    ### Book a recall
    And Maria clicks on the Book a recall link
    And Maria enters the licence name
    And Maria enters the pre-convictions name
    And Maria confirms the person is in custody
    And Maria submits the date and email of the recall request received from probation service
    And Maria submits the sentence, offence and release details
    And Maria submits the police contact details
    And Maria submits any vulnerability and contraband related details for the offender
    And Maria submits the probation officer details
    And Maria uploads some documents
    And Maria submits the reason for missing documents
    Then Maria can check their answers
    And Maria uploads missing documents
    And Maria downloads the documents
    Then Maria completes the booking

    ### Rescind the recall
    When Maria goes to the view recall page
    And Maria rescinds the recall
    Then Maria checks that the recall has been rescinded
