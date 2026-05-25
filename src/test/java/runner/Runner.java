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
        // Default: @PSLTests
        // Override at runtime: mvn test -Dcucumber.filter.tags="@IPLTests"
        tags = "@PSLTests"
)
public class Runner extends AbstractTestNGCucumberTests {

    private static final Logger LOGGER = Logger.getLogger(Runner.class.getName());

    // Track execution details
    private static String executedBrowser = "unknown";
    private static String executedTags = "unknown";

    /**
     * Override scenarios() to manage parallel/sequential execution.
     */
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    /**
     * @AfterSuite hook - runs AFTER all test scenarios complete.
     */
    @AfterSuite
    public void sendEmailReport() {
        try {
            System.out.println("========================================");
            System.out.println("Sending Final Execution Email...");
            System.out.println("========================================");

            // Get browser from Constants (source: config.properties)
            String browserUsed = Constants.BROWSER;
            LOGGER.info("[Runner] Browser used in execution: " + browserUsed);
            executedBrowser = browserUsed;

            // Get tags dynamically (via CLI or Reflection)
            String executedTag = getExecutedTags();
            LOGGER.info("[Runner] Tags executed: " + executedTag);
            executedTags = executedTag;

            // Call EmailUtilities to send report
            EmailUtilities.sendExecutionReport(browserUsed, executedTag);

            System.out.println("Final email sent successfully!");
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("Failed to send final email: " + e.getMessage());
            LOGGER.log(java.util.logging.Level.SEVERE, "[Runner] Email send failed", e);
        }
    }

    /**
     * Get the tags being executed dynamically.
     * * Priority:
     * 1. System property override (e.g., via CLI: -Dcucumber.filter.tags="@IPLTests")
     * 2. Reflection fallback reading the active tag directly from the @CucumberOptions annotation
     */
    private static String getExecutedTags() {
        // Option 1: Get tags from system property (overridable at runtime)
        String tagsFromSystem = System.getProperty("cucumber.filter.tags");

        if (tagsFromSystem != null && !tagsFromSystem.isEmpty()) {
            LOGGER.info("[Runner] Tags from system property: " + tagsFromSystem);
            return tagsFromSystem;
        }

        // Option 2: Dynamically parse the active @CucumberOptions tag using reflection
        try {
            CucumberOptions cucumberOptions = Runner.class.getAnnotation(CucumberOptions.class);
            if (cucumberOptions != null && !cucumberOptions.tags().isEmpty()) {
                String tagsFromAnnotation = cucumberOptions.tags();
                LOGGER.info("[Runner] Tags dynamically read from @CucumberOptions: " + tagsFromAnnotation);
                return tagsFromAnnotation;
            }
        } catch (Exception e) {
            LOGGER.warning("[Runner] Failed to read @CucumberOptions via reflection: " + e.getMessage());
        }

        // Absolute Fallback if no tag settings are found anywhere
        return "No Tags Defined";
    }

    /**
     * Get executed browser for external access.
     */
    public static String getExecutedBrowser() {
        return executedBrowser;
    }

    /**
     * Get executed tags for external access.
     */
    public static String getExecutedTagsValue() {
        return executedTags;
    }
}
