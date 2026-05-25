package runner;

import base.BaseClass;
import constants.Constants;
import base.utilities.EmailUtilities;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;

import java.util.logging.Logger;

/**
 * Runner - Cucumber TestNG test runner for Cricbuzz automation framework.
 *
 * Purpose:
 *  - Configure Cucumber options (feature location, glue, plugins, tags)
 *  - Extend AbstractTestNGCucumberTests to integrate Cucumber with TestNG
 *  - Execute Cucumber scenarios in parallel or sequentially
 *  - Send execution report via email after test suite completes
 *
 * Cucumber Configuration Flow:
 *  1. @CucumberOptions defines feature files, step definitions, report plugins
 *  2. Scenarios loaded from src/test/resources/features/
 *  3. Step definitions glued from stepDefinitions package
 *  4. HTML + JSON reports generated under target/
 *  5. @AfterSuite sends email with execution details
 *
 * Test Execution:
 *  - Run via: mvn test
 *  - Or: right-click Runner.java → Run as TestNG Test (IDE)
 *  - Or: via Cucumber CLI
 *
 * Reports Generated:
 *  - target/cucumber-html-report.html (HTML report for browser viewing)
 *  - target/cucumber.json (JSON report for CI/CD integration)
 *  - Screenshots captured on failure (stored in Screenshots/ folder)
 *  - Email report sent via EmailUtilities.sendExecutionReport()
 */
@CucumberOptions(
        // Feature files location
        features = "src/test/resources/features",

        // Glue path: where step definitions and hooks are located
        glue = {"stepDefinitions"},

        // Report plugins: HTML, JSON, console output
        plugin = {
                "pretty",                                    // Pretty console output
                "html:target/cucumber-html-report.html",    // HTML report
                "json:target/cucumber.json"                 // JSON report (for CI/CD)
        },

        // Monochrome: make console output readable (no ANSI color codes)
        monochrome = true,

        // Tags: filter scenarios to run
        // Default: @IPLTests
        // Override at runtime: mvn test -Dcucumber.filter.tags="@PSLTests"
        tags = "@IPLTests"
)
public class Runner extends AbstractTestNGCucumberTests {

    private static final Logger LOGGER = Logger.getLogger(Runner.class.getName());

    // Track execution details
    private static String executedBrowser = "unknown";
    private static String executedTags = "unknown";

    /**
     * Override scenarios() to manage parallel/sequential execution.
     *
     * Purpose:
     *  - Provide scenarios for TestNG data provider
     *  - Control test execution (parallel or sequential)
     *  - Allow TestNG to distribute scenarios across threads
     *
     * Current Configuration:
     *  - parallel = false → scenarios run sequentially (one after another)
     *  - Set to true if you want parallel execution (with thread-safe drivers)
     *
     * Where It's Called:
     *  - TestNG Framework: automatically calls this as DataProvider
     *  - Loads scenarios from feature files based on @CucumberOptions
     *
     * @return Object[][] array of scenarios (each row is one scenario)
     */
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        // Call parent implementation to get scenarios from feature files
        return super.scenarios();
    }

    /**
     * @AfterSuite hook - runs AFTER all test scenarios complete.
     *
     * Purpose:
     *  - Send execution report via email
     *  - Include browser used and tags executed in email subject
     *  - Handle exceptions gracefully (don't fail if email send fails)
     *
     * Configuration Source:
     *  - Browser: read from Constants.BROWSER (loaded from config.properties)
     *  - Tags: read from @CucumberOptions tags field above
     *  - Email credentials: read from config.properties by EmailUtilities
     *
     * Flow:
     *  1. Test suite completes (all scenarios run)
     *  2. @AfterSuite triggered automatically
     *  3. Log email send start message
     *  4. Get browser from Constants (instead of hard-coding)
     *  5. Get tags from @CucumberOptions (instead of hard-coding)
     *  6. Call EmailUtilities.sendExecutionReport(browser, tags)
     *     → Reads email credentials from config.properties
     *     → Attaches HTML report from target/
     *     → Sends to recipient
     *  7. Log success or error message
     *  8. Store execution details for potential logging/reporting
     *
     * Email Behavior:
     *  - If email credentials missing: EmailUtilities logs warning and skips sending (safe)
     *  - If report file not found: EmailUtilities logs warning and sends email without attachment
     *  - If email send fails: exception caught, logged, execution continues (doesn't break test)
     *
     * Lifecycle:
     *  - Runs once at the very end of test suite (after tearDown of last scenario)
     *  - Has access to all test results via TestNG listener mechanisms
     *
     * Where It's Called:
     *  - TestNG Framework: automatically after @AfterSuite
     *  - Timing: after all scenarios executed, fixtures cleaned up
     */
    @AfterSuite
    public void sendEmailReport() {
        try {
            System.out.println("========================================");
            System.out.println("Sending Final Execution Email...");
            System.out.println("========================================");

            // Get browser from Constants (source: config.properties)
            // Instead of hard-coding "chrome"
            // This reflects the actual browser used in execution
            String browserUsed = Constants.BROWSER;
            LOGGER.info("[Runner] Browser used in execution: " + browserUsed);
            executedBrowser = browserUsed;

            // Get tags from @CucumberOptions
            // Instead of hard-coding "@PSLTests"
            // This reflects the actual tags executed
            String executedTag = getExecutedTags();
            LOGGER.info("[Runner] Tags executed: " + executedTag);
            executedTags = executedTag;

            // Call EmailUtilities to send report
            // Source: config.properties (email credentials)
            // Attachment: target/cucumber-html-report.html (or target/cucumber-html-report/index.html)
            // Recipient: config.properties key "cricbuzz.receiver" (defaults to bm13.asb@gmail.com)
            EmailUtilities.sendExecutionReport(browserUsed, executedTag);

            System.out.println("Final email sent successfully!");
            System.out.println("========================================");

        } catch (Exception e) {
            // Catch any exception during email send
            // Log error but do NOT re-throw (prevents test failure due to email issues)
            System.err.println("Failed to send final email: " + e.getMessage());
            LOGGER.log(java.util.logging.Level.SEVERE, "[Runner] Email send failed", e);
            // Continue execution (don't fail the test suite)
        }
    }

    /**
     * Get the tags being executed.
     *
     * Purpose:
     *  - Return the tag filter used in @CucumberOptions
     *  - Reflects actual scenarios being run
     *  - Used in email report subject
     *
     * Current Implementation:
     *  - Returns hardcoded value from @CucumberOptions tags field
     *  - This is the tag filter applied to run specific scenarios
     *
     * Future Improvement:
     *  - Could extract actual executed tags dynamically from scenario context
     *  - Could get tags from system property: System.getProperty("cucumber.filter.tags")
     *  - Could track tags from each executed scenario and report actual results
     *
     * Source:
     *  - Currently: @CucumberOptions tags = "@IPLTests"
     *  - Could be overridden at runtime: mvn test -Dcucumber.filter.tags="@PSLTests"
     *
     * @return String tag filter (e.g., "@IPLTests", "@PSLTests")
     */
    private static String getExecutedTags() {
        // Option 1: Return hardcoded value from @CucumberOptions
        // This is the tag filter configured in the runner
        String tagsFromOptions = "@IPLTests";

        // Option 2 (Future): Get tags from system property (overridable at runtime)
        String tagsFromSystem = System.getProperty("cucumber.filter.tags");

        // Use system property if set, otherwise use hardcoded value
        if (tagsFromSystem != null && !tagsFromSystem.isEmpty()) {
            LOGGER.info("[Runner] Tags from system property: " + tagsFromSystem);
            return tagsFromSystem;
        }

        // Fallback to hardcoded value from @CucumberOptions
        LOGGER.info("[Runner] Tags from @CucumberOptions: " + tagsFromOptions);
        return tagsFromOptions;
    }

    /**
     * Get executed browser for external access.
     *
     * Purpose:
     *  - Allow other classes to retrieve browser used in execution
     *  - Useful for logging, reporting, or dynamic behavior
     *
     * @return Browser name used in test execution (e.g., "chrome", "firefox")
     */
    public static String getExecutedBrowser() {
        return executedBrowser;
    }

    /**
     * Get executed tags for external access.
     *
     * Purpose:
     *  - Allow other classes to retrieve tags used in execution
     *  - Useful for logging, reporting, or dynamic behavior
     *
     * @return Tags filter used in test execution (e.g., "@IPLTests")
     */
    public static String getExecutedTagsValue() {
        return executedTags;
    }
}