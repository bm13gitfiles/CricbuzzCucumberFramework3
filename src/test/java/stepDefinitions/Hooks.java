package stepDefinitions;

import base.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hooks - Cucumber lifecycle hooks for test setup and teardown.
 *
 * Purpose:
 *  - Manage WebDriver initialization before each test scenario
 *  - Capture screenshots when tests fail
 *  - Clean up WebDriver after each test scenario
 *  - Ensure consistent pre/post-test state for all scenarios
 *
 * Lifecycle Flow:
 *  1. Feature file (Cucumber) triggers @Before hook
 *  2. setUp() initializes WebDriver
 *  3. Scenario steps execute (using getDriver() to interact with browser)
 *  4. If step fails: takeScreenshotOnFailure() captures screenshot
 *  5. tearDown() quits WebDriver
 *  6. Next scenario repeats cycle
 *
 * Configuration Source:
 *  - src/main/resources/config.properties
 *    → BaseClass reads cricbuzz.browser, cricbuzz.implicitWaitSeconds
 *
 * Integration:
 *  - Hooks are auto-discovered by Cucumber (glue path in Runner.java)
 *  - Called automatically before/after each scenario (@Before/@After annotations)
 */
public class Hooks {

    /**
     * @Before hook - runs BEFORE each Cucumber scenario starts.
     *
     * Purpose:
     *  - Initialize a fresh WebDriver instance
     *  - Prepare the browser for the upcoming test scenario
     *  - Ensure consistent starting state
     *
     * Configuration Source:
     *  - src/main/resources/config.properties
     *    - cricbuzz.browser: browser type to launch (e.g., "chrome")
     *    - cricbuzz.implicitWaitSeconds: implicit wait timeout (e.g., "10")
     *
     * Flow:
     *  1. Print "Initializing the browser..." message to console
     *  2. Call BaseClass.initializeDriver()
     *     → BaseClass loads config.properties
     *     → Instantiates WebDriver (Chrome/Firefox/Edge)
     *     → Maximizes window
     *     → Sets implicit wait timeout
     *  3. Print success message with browser name to console
     *
     * Where it goes to:
     *  - WebDriver instance stored in BaseClass.driver (static)
     *  - Step definitions use BaseClass.getDriver() to access the driver
     *  - All test steps interact with this browser instance
     *
     * Execution Order:
     *  - Runs once per scenario (before any steps)
     *  - Multiple scenarios run sequentially, each gets fresh driver
     */
    @Before
    public void setUp() {
        // Print startup message to console and test report
        System.out.println("Initializing the browser...");

        // Initialize WebDriver
        // Source: BaseClass.initializeDriver() reads from config.properties
        // Action: Creates WebDriver instance, maximizes window, sets timeout
        // Destination: WebDriver stored in BaseClass.driver (static singleton)
        BaseClass.initializeDriver();

        // Print confirmation message showing browser was launched
        System.out.println("Browser launched successfully");
    }

    /**
     * @After hook with order=1 - runs AFTER each scenario IF it FAILS (first cleanup step).
     *
     * Purpose:
     *  - Capture screenshot of the browser state when a test fails
     *  - Attach screenshot to Cucumber report for evidence/debugging
     *  - Order=1 means this runs BEFORE the next @After hook (order=0)
     *
     * Scenario Parameter:
     *  - Source: Cucumber framework automatically injects Scenario object
     *  - Contains: scenario name, status (passed/failed), step details
     *
     * Flow:
     *  1. Check if scenario.isFailed() — is the test marked as failed?
     *  2. If failed:
     *     a. Get WebDriver from BaseClass.getDriver()
     *     b. Cast WebDriver to TakesScreenshot interface
     *     c. Call getScreenshotAs(OutputType.BYTES) → capture screenshot as byte array
     *     d. Attach bytes to scenario report via scenario.attach()
     *     e. Print success message to console
     *  3. If not failed: do nothing (skip screenshot capture)
     *  4. Catch exceptions: if screenshot fails, log error and continue
     *
     * Where it goes to:
     *  - Screenshot bytes → attached to Cucumber scenario in the report
     *  - Report location: target/cucumber-html-report/index.html
     *  - Screenshot appears in the failure evidence section of the report
     *  - Console output: printed to test execution logs
     *
     * Execution Order:
     *  - Runs after scenario completes (if failed)
     *  - Runs BEFORE tearDown() (order=1 > order=0)
     *  - If multiple @After hooks exist, lower order values run later
     *
     * @param scenario Cucumber Scenario object (injected by framework)
     */
    @After(order = 1)
    public void takeScreenshotOnFailure(Scenario scenario) {
        // Check if the scenario failed
        // Source: Cucumber framework evaluates test result
        if (scenario.isFailed()) {
            try {
                // Get WebDriver instance
                // Source: BaseClass.getDriver() returns the static driver instance
                // Cast to TakesScreenshot interface to enable screenshot capability
                final byte[] screenshot =
                        ((TakesScreenshot) BaseClass.getDriver()).getScreenshotAs(OutputType.BYTES);

                // Attach screenshot to Cucumber scenario report
                // Destination: target/cucumber-html-report/index.html (scenario failure details)
                // Parameters:
                //  - screenshot: byte array of screenshot
                //  - "image/png": MIME type of the image
                //  - "Failure Screenshot": descriptive name in report
                scenario.attach(screenshot, "image/png", "Failure Screenshot");

                // Print confirmation message to console/logs
                System.out.println("Screenshot taken for failed scenario: " + scenario.getName());

            } catch (Exception e) {
                // If screenshot capture fails, catch exception and continue
                // This prevents test cleanup from breaking if screenshot fails
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    /**
     * @After hook with order=0 - runs AFTER each scenario (last cleanup step).
     *
     * Purpose:
     *  - Close the browser
     *  - Terminate WebDriver process
     *  - Clean up resources (memory, system processes)
     *  - Reset driver for next scenario
     *
     * Order=0 Significance:
     *  - Runs AFTER all other @After hooks (higher order values run first)
     *  - Ensures screenshot is captured BEFORE driver is quit
     *  - Ensures screenshot is attached to report BEFORE browser closes
     *
     * Flow:
     *  1. Print "Closing the browser..." message to console
     *  2. Call BaseClass.quitDriver()
     *     → Calls driver.quit() to close all browser windows
     *     → Terminates WebDriver process
     *     → Sets BaseClass.driver = null (resets singleton)
     *  3. Print success message to console
     *  4. Next scenario will call setUp() → initializeDriver() creates new driver instance
     *
     * Where it goes to:
     *  - Browser windows: all closed
     *  - WebDriver process: terminated
     *  - BaseClass.driver: reset to null
     *  - Console output: printed to logs
     *
     * Execution Order:
     *  1. takeScreenshotOnFailure() runs first (order=1)
     *  2. tearDown() runs last (order=0)
     *  3. Next scenario starts fresh with new setUp() call
     *
     * Why this order matters:
     *  - If we quit driver BEFORE taking screenshot, screenshot would fail
     *  - This order ensures capture-then-cleanup sequence
     */


    @After(order = 0)
    public void tearDown() {
        // Print cleanup start message to console
        System.out.println("Closing the browser...");

        // Quit WebDriver and clean up resources
        // Source: BaseClass.getDriver() gets the active driver
        // Action: BaseClass.quitDriver() calls driver.quit() and sets driver=null
        // Destination: browser windows closed, system resources freed, driver reset
        BaseClass.quitDriver();

        // Print confirmation message
        System.out.println("Driver quit successfully");
    }
}