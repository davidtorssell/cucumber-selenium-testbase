@Selenium
Feature: Handelsbanken poc

  Scenario: Verify Handelsbanken logo on front page
    Given I am at the Handelsbanken front page
    Then there should be a big Handelsbanken logotype


  Scenario Outline: Verify nav bar items
    Given I am at the Handelsbanken front page
    When clicking the "<MenuItemName>"
    Then I should see the equivalent page for "<MenuItemName>"

    Examples:
      | MenuItemName              |
      | Start                     |
      | Lån                       |
      | Spara                     |
      | Pension                   |

