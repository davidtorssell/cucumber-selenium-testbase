@Selenium
Feature: Playground and experimentation

  Scenario: Verify hit when searching for Davids LinkedIn profile
    Given I am at the google front page
    When I search for "david torssell linkedin"
    Then there should be a hit with the address to Davids profile

  Scenario: Search for something random
    Given I am at the google front page
    When I search for "italian spiderman"
    Then there should be a picture of italian spiderman
      And it should be awesome
