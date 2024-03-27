Feature: validate the working of redbus, select bus seat
  Scenario: verify the working of bus seat selection
    Given enter the from address "" and to address "" and select date "" and click on search
    Then verify user able see the list of busses from given addresses and date
    And clich the bus name "" and select seat and pickup stop "" and drop stop "" and click on select
    Then take screenshot ""