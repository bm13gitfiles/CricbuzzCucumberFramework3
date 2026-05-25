**CricbuzzCucumberFramework3**

A comprehensive Behavior-Driven Development (BDD) automation testing framework for the Cricbuzz website, built with Cucumber, Selenium, and TestNG. This framework validates IPL (Indian Premier League), PSL (Pakistan Super League), and ICC Rankings pages with data-driven testing, detailed reporting, and email notifications



**Project Overview:**


CricbuzzCucumberFramework3 is a production-ready test automation framework designed to:
- Automate web UI testing for Cricbuzz using Gherkin scenarios
- Validate critical features: Series pages (IPL/PSL), news sections, points tables, player statistics, teams/captains, and rankings
- Capture evidence: Screenshots on failure, attached to HTML reports and email notifications
- Support multiple browsers: Chrome, Firefox, Edge (configurable)
- Generate detailed reports: HTML and JSON formats for CI/CD integration
- Send automated emails: Execution reports with attachments to stakeholders


**Key Benefits**

- BDD approach for readability by non-technical stakeholders
- Centralized configuration, page objects, and reusable utilities for maintainability
- Scalable design for adding new test cases and pages
- CI/CD ready with JSON reports and email notifications
- Production quality with error handling, logging, and secure credentials


**Quick Start**

**Prerequisites**

- Java 17+
- Maven 3.6+
- Browser & WebDriver (Chrome/Firefox/Edge)
- Gmail account (optional, for email reports with App Password)


Setup & Installation

- Clone the repository  - `git clone https://github.com/cricbuzz/CricbuzzCucumberFramework3.git`
- Navigate to the project directory  - `cd CricbuzzCucumberFramework3`
- Install dependencies  - `mvn clean install`
- Configure `config.properties` with browser choice, email settings, and other parameters (cricbuzz.browser=chrome
  )
- Build the project:  - `mvn clean package`
- Run tests:  - `mvn test`

Project Structure:

CricbuzzCucumberFramework3/
├── src/
│   ├── main/java/
│   │   ├── base/                # WebDriver lifecycle
│   │   ├── utilities/           # Email utilities
│   │   ├── constants/           # Config constants
│   │   └── webCommons/          # Selenium utilities
│   ├── test/java/
│   │   ├── stepDefinitions/     # Hooks & step definitions
│   │   ├── pageObjects/         # Page locators & elements
│   │   ├── runner/              # Cucumber TestNG runner
│   │   └── resources/           # Config & feature files
│   └── pom.xml                  # Maven dependencies
├── target/                      # Reports
├── Screenshots/                 # Failure evidence
├── README.md
└── .gitignore



Test Scenarios
- IPL Series Tests (8 scenarios): News validation, points table, most runs/wickets, teams & captains
- PSL Series Tests (8 scenarios): Similar validations for PSL pages
- Rankings Tests (10+ scenarios): Men’s & Women’s rankings across ODI and T20I formats



Configuration

All configuration is externalized in src/main/resources/config.properties:

(i.e)cricbuzz.browser=chrome , cricbuzz.implicitWaitSeconds=10

Running Tests
- Run all tests - bash mvn clean test
- Run specific feature - bash mvn clean test -Dcucumber.options="--tags @IPLSeries"
- Run with specific browser: edit config.properties → cricbuzz.browser=firefox


Test Reports
- HTML Report: target/cucumber-html-report.html
- JSON Report: target/cucumber.json (for CI/CD)
- Screenshots: Captured on failure in Screenshots/WebElements/
- Email Report: Optional, sent with execution details and HTML attachment


Code Architecture Highlights
- Lazy Initialization Pattern for WebDriver
- Page Object Model for centralized locators
- Reusable Utilities (WebCommons) with 30+ helper methods
- Externalized Configuration for maintainability
- Comprehensive Logging with evidence attached to reports


Best Practices Implemented
- Singleton WebDriver instance
- Explicit waits for stability
- Stale element handling
- Exception handling with logging
- Secure credentials via environment variables
- Javadoc documentation for all methods
- CI/CD ready with JSON reports and email notifications

















