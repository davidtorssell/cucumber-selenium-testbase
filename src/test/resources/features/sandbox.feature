@Selenium
Feature: Playground and experimentation

  Scenario: Verify hit when searching for Davids LinkedIn profile
    Given I am at the google front page
    When I search for "david torssell linkedin"
    Then there should be a hit with the address to Davids profile