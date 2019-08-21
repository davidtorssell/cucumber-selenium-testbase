@Selenium
Feature: Playground and experimentation

  Scenario: Verify hit when searching for Davids LinkedIn profile
    Given I am at the google front page
    When I search for "xkcd"
    Then it should be awesome

  Scenario: Search for something random
    Given I am at the google front page
    When I search for "kung fury"
    Then there should be a picture of italian spiderman
      And it should be awesome
