# cucumber-selenium-testbase

A base system for testing web applications using Cucumber and Selenium

Features:
- Driver reuse between scenarios for performance set by default.
- Cucumber reports
- Screenshots for failed scenarios
- Execution via jUnit or Cucumber java
- Configurable for different local or remote browsers (Selenium grid)
- Basic page object structure
- Some stabilization for flaky target systems
- Configuration such as target browser, environment or test suite can be configured in test.properties or overrided by mvn flags

Requires:
- Java 8
- Webdriver on path or selenium grid

Usage
- Execution:
  - Run with jUnit or cucumber runner in ide or
  - mvn test...
  - Create SUT that extends SharedSeleniumImplementation to reuse driver between scenarios