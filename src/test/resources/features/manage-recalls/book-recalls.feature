Feature: Book a recall

  Background:
    Given Maria is logged in to the manage recalls service


  Scenario: book a recall successfully
    Given Maria has received a recall recommendation for a prison offender with NOMIS ID "A1234AA" from probation service
    When she books a recall
    Then the recall is successfully booked