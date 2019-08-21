@Selenium
Feature: Handelsbanken offices

  Scenario: Verify Handelsbanken logo on front page
    Given I am at the Handelsbanken front page
    When I search for office with "sergel"
    Then the address to the Sergel office should be displayed
