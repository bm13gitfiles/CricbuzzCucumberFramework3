package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BaseClass - Central WebDriver management for the Cricbuzz automation framework.
 *
 * Purpose:
 *  - Manages singleton WebDriver instance lifecycle (create, retrieve, destroy)
 *  - Reads browser configuration from src/main/resources/config.properties
 *  - Provides static methods for WebDriver access across all test classes
 *  - Ensures consistent browser setup and teardown across all test scenarios
 *
 * Design Pattern:
 *  - Singleton: Only one WebDriver instance per test run
 *  - Static methods: Accessible from anywhere in the project without instantiation
 *
 * Configuration Flow:
 *  config.properties (static block loads on class initialization)
 *  → cricbuzz.browser property read
 *  → cricbuzz.implicitWaitSeconds property read
 *  → initializeDriver() creates WebDriver with these values
 *  → getDriver() provides access to WebDriver in step definitions
 *  → quitDriver() performs cleanup
 *
 * Integration Points:
 *  - Hooks.setUp() → calls initializeDriver() (before each scenario)
 *  - Step definitions → call getDriver() (to interact with web elements)
 *  - Hooks.takeScreenshotOnFailure() → calls getDriver() (to take screenshots)
 *  - Hooks.tearDown() → calls quitDriver() (after each scenario)
 *
 * Lifecycle:
 *  1. Test starts → Hooks.setUp() triggers
 *  2. setUp() calls BaseClass.initializeDriver()
 *  3. initializeDriver() loads config.properties and creates WebDriver
 *  4. Test steps call BaseClass.getDriver() to interact with browser
 *  5. Test ends (passed or failed) → Hooks.tearDown() triggers
 *  6. tearDown() calls BaseClass.quitDriver()
 *  7. quitDriver() closes browser and resets driver to null
 *  8. Next test scenario repeats cycle with fresh driver
 */
public class BaseClass {

    // ============================================
    // STATIC FIELDS & INITIALIZATION
    // ============================================

    /**
     * Logger instance for debugging and information messages.
     * Source: Java's built-in java.util.logging
     * Usage: Log config load, driver init, timeout settings
     * Destination: Console and/or log files (based on logging configuration)
     */
    private static final Logger LOGGER = Logger.getLogger(BaseClass.class.getName());

    /**
     * Static WebDriver instance - shared across entire test execution.
     *
     * Lifecycle:
     *  - Initially: null
     *  - After initializeDriver(): points to Chrome/Firefox/Edge instance
     *  - After quitDriver(): reset to null (for next scenario's fresh start)
     *
     * Scope: static (not instance-specific, shared by all classes)
     * Access: via getDriver() method (read-only after initialization)
     */
    protected static WebDriver driver;

    /**
     * Properties object to hold configuration key-value pairs.
     *
     * Source: Loaded from src/main/resources/config.properties at class startup
     * Contents: Browser name, implicit wait timeout, email settings
     * Keys used:
     *  - "cricbuzz.browser" → browser type (chrome/firefox/edge)
     *  - "cricbuzz.implicitWaitSeconds" → timeout in seconds
     *
     * Lifecycle: Loaded once (in static initializer block) and reused
     */
    private static final Properties PROPS = new Properties();

    /**
     * Static initializer block - runs exactly ONCE when BaseClass is first loaded.
     *
     * Purpose:
     *  - Load configuration from properties file into memory
     *  - Establish default settings before any test runs
     *  - Log configuration status
     *
     * Configuration Source:
     *  File: src/main/resources/config.properties
     *  Keys:
     *    - cricbuzz.browser=chrome (browser type)
     *    - cricbuzz.implicitWaitSeconds=10 (timeout)
     *    - cricbuzz.fromEmail=... (sender email)
     *    - cricbuzz.appPassword=... (app password)
     *    - cricbuzz.receiver=... (recipient email)
     *
     * Flow:
     *  1. ClassLoader.getResourceAsStream("config.properties")
     *     → Loads file from classpath (compiled into target/classes/)
     *  2. PROPS.load(in)
     *     → Parses key=value pairs into PROPS map
     *  3. Success: Log info message
     *  4. File not found: Log warning, continue with defaults
     *  5. Parse error: Log warning, continue with defaults
     *
     * Destination:
     *  - PROPS object (in-memory)
     *  - Used by initializeDriver() to read browser and timeout
     *  - Used by other classes (e.g., EmailUtilities) to read email settings
     *
     * Exception Handling:
     *  - Graceful fallback: if properties not found, app continues
     *  - Defaults in code: "chrome" browser, "10" second timeout
     *  - No exceptions thrown (try-catch ensures safety)
     */
    static {
        // Attempt to load config.properties from classpath
        try (InputStream in = BaseClass.class.getClassLoader().getResourceAsStream("config.properties")) {
            // Check if file was found
            if (in != null) {
                // File exists: load properties from input stream
                PROPS.load(in);
                // Log successful load
                LOGGER.info("[BaseClass] Loaded configuration from config.properties");
            } else {
                // File not found: log warning
                LOGGER.warning("[BaseClass] config.properties not found; using defaults");
            }
        } catch (Exception e) {
            // Any error during loading: log warning and gracefully continue
            LOGGER.log(Level.WARNING, "[BaseClass] Error loading config.properties", e);
            // App does NOT crash; defaults in initializeDriver() will be used
        }
    }

    // ============================================
    // PUBLIC METHODS - WebDriver Lifecycle
    // ============================================

    /**
     * Initialize WebDriver based on configuration from config.properties.
     *
     * Purpose:
     *  - Create a new WebDriver instance (singleton pattern)
     *  - Configure browser (Chrome/Firefox/Edge)
     *  - Set implicit wait timeout
     *  - Maximize browser window
     *  - Ensure only one WebDriver per test run
     *
     * Singleton Pattern:
     *  - First call: if (driver == null) → creates new instance
     *  - Subsequent calls: if (driver != null) → returns existing instance
     *  - Prevents multiple browser windows from opening
     *
     * Configuration Sources:
     *  1. PROPS object (populated by static initializer from config.properties)
     *     Key: "cricbuzz.browser" → e.g., "chrome"
     *     Key: "cricbuzz.implicitWaitSeconds" → e.g., "10"
     *
     *  2. Default fallback (if properties not loaded):
     *     Browser default: "chrome"
     *     Timeout default: "10" seconds
     *
     *  3. Environment variables (alternative, not used in this method)
     *
     * Execution Flow:
     *  Step 1: Check singleton condition (driver == null?)
     *     If driver already exists, return it (avoid reinitializing)
     *
     *  Step 2: Read browser name from PROPS
     *     Command: PROPS.getProperty("cricbuzz.browser", "chrome")
     *     Default: "chrome" if key not found
     *     Result: browserName variable set to config value
     *
     *  Step 3: Read timeout value from PROPS
     *     Command: PROPS.getProperty("cricbuzz.implicitWaitSeconds", "10")
     *     Default: "10" if key not found
     *     Result: waitStr variable set to config value (as string)
     *
     *  Step 4: Parse timeout string to integer
     *     Try: Integer.parseInt(waitStr) → convert "10" to 10
     *     If fail (e.g., "abc"): catch exception, log warning, use default 10
     *     Result: implicitWaitSeconds variable set to integer value
     *
     *  Step 5: Log configuration
     *     Example: "[BaseClass] Initializing browser: chrome with implicit wait: 10 seconds"
     *     Destination: Console and/or log file
     *
     *  Step 6: Create WebDriver instance based on browser type
     *     Cases:
     *       "chrome" → new ChromeDriver() (requires chromedriver on PATH)
     *       "firefox" → new FirefoxDriver() (requires geckodriver on PATH)
     *       "edge" → new EdgeDriver() (requires msedgedriver on PATH)
     *       other → throw IllegalArgumentException (fail fast)
     *     Result: driver variable now holds WebDriver instance
     *
     *  Step 7: Configure browser window
     *     Command: driver.manage().window().maximize()
     *     Result: Browser opens in maximized state for better visibility
     *
     *  Step 8: Set implicit wait timeout
     *     Command: driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds))
     *     Effect: WebDriver waits up to 10 seconds (or configured value) before throwing NoSuchElementException
     *     Applies to: All findElement() calls throughout test execution
     *
     *  Step 9: Return driver instance
     *     Destination: Caller gets reference to initialized WebDriver
     *
     * Where It's Called:
     *  - Hooks.setUp() (before each scenario) → BaseClass.initializeDriver()
     *
     * Where The Result Goes:
     *  - driver variable (static, module-scoped)
     *  - Accessed via getDriver() in step definitions and page objects
     *  - Example: WebElement button = BaseClass.getDriver().findElement(By.id("btn"))
     *
     * Error Handling:
     *  - Invalid browser type: throw IllegalArgumentException (test fails immediately)
     *  - Invalid timeout format: log warning, use default 10 seconds
     *  - WebDriver instantiation fails: exception propagates (test setup fails)
     *
     * @return WebDriver instance (initialized and ready for use)
     */
    public static WebDriver initializeDriver() {
        // STEP 1: Singleton check - has driver already been created?
        if (driver == null) {
            // STEP 2: Read browser name from config.properties
            // Source: PROPS.getProperty("cricbuzz.browser", default)
            // Key: "cricbuzz.browser" (e.g., "chrome", "firefox", "edge")
            // Default: "chrome" if key not found or empty
            // Trim: remove leading/trailing whitespace
            String browserName = PROPS.getProperty("cricbuzz.browser", "chrome").trim();

            // STEP 3: Read implicit wait timeout from config.properties
            // Source: PROPS.getProperty("cricbuzz.implicitWaitSeconds", default)
            // Key: "cricbuzz.implicitWaitSeconds" (e.g., "10")
            // Default: "10" if key not found or empty
            // Note: Still a string at this point; will parse to integer next
            // Trim: remove leading/trailing whitespace
            String waitStr = PROPS.getProperty("cricbuzz.implicitWaitSeconds", "10").trim();

            // STEP 4: Parse timeout string to integer
            int implicitWaitSeconds;
            try {
                // Convert string to integer
                // Input: "10" (string from config.properties)
                // Output: 10 (integer value)
                implicitWaitSeconds = Integer.parseInt(waitStr);
            } catch (NumberFormatException e) {
                // If parsing fails (e.g., "abc" can't be parsed to int)
                // Log warning with exception details
                LOGGER.log(Level.WARNING, "[BaseClass] Invalid implicit wait value: " + waitStr + "; using default 10", e);
                // Use default timeout of 10 seconds
                implicitWaitSeconds = 10;
            }

            // STEP 5: Log the configuration being used
            // Destination: Console, log files (based on logging configuration)
            // Example output: "[BaseClass] Initializing browser: chrome with implicit wait: 10 seconds"
            LOGGER.info("[BaseClass] Initializing browser: " + browserName + " with implicit wait: " + implicitWaitSeconds + " seconds");

            // STEP 6: Instantiate WebDriver based on browser type
            // Source: browserName read from config.properties above
            // Actions: Create Chrome/Firefox/Edge driver instance
            // Destination: driver variable (static field)
            switch (browserName.toLowerCase()) {
                case "chrome":
                    // Launch Google Chrome browser
                    // Requires: chromedriver.exe on PATH or specify webdriver.chrome.driver property
                    driver = new ChromeDriver();
                    break;

                case "firefox":
                    // Launch Mozilla Firefox browser
                    // Requires: geckodriver.exe on PATH or specify webdriver.firefox.driver property
                    driver = new FirefoxDriver();
                    break;

                case "edge":
                    // Launch Microsoft Edge browser
                    // Requires: msedgedriver.exe on PATH or specify webdriver.edge.driver property
                    driver = new EdgeDriver();
                    break;

                default:
                    // Unsupported browser type: throw exception immediately (fail fast)
                    // This forces test failure if browser configuration is wrong
                    throw new IllegalArgumentException("Browser not supported: " + browserName);
            }

            // STEP 7: Maximize browser window
            // Source: driver instance created above
            // Action: Opens browser in full-screen (maximized) state
            // Purpose: Better visibility of web elements during test execution
            driver.manage().window().maximize();

            // STEP 8: Set implicit wait timeout
            // Source: implicitWaitSeconds parsed from config.properties
            // Action: Configure WebDriver implicit wait behavior
            // Duration: Duration.ofSeconds(implicitWaitSeconds) = wait time before exception
            // Effect: All findElement() calls will wait up to this duration before throwing NoSuchElementException
            // Scope: Applies globally to all subsequent findElement() calls in this driver instance
            // Example: If timeout is 10 seconds and element appears in 3 seconds, findElement returns immediately
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitSeconds));
        }

        // STEP 9: Return the WebDriver instance
        // Destination: Caller (usually Hooks.setUp()) gets the initialized driver
        // Usage: Stored in static field driver for access via getDriver()
        return driver;
    }

    /**
     * Get the current active WebDriver instance.
     *
     * Purpose:
     *  - Provide read-only access to the WebDriver
     *  - Allow step definitions and page objects to interact with the browser
     *  - Central access point (static method, no instantiation needed)
     *
     * How It Works:
     *  - Returns the static driver variable created by initializeDriver()
     *  - null if initializeDriver() was never called
     *
     * Where It's Called:
     *  - Step definitions: to find elements and perform actions
     *    Example: BaseClass.getDriver().findElement(By.xpath("//button")).click()
     *  - Page objects: to initialize web elements
     *    Example: WebElement button = BaseClass.getDriver().findElement(By.id("btn"))
     *  - Hooks.takeScreenshotOnFailure(): to cast to TakesScreenshot for screenshot capture
     *    Example: ((TakesScreenshot) BaseClass.getDriver()).getScreenshotAs(OutputType.BYTES)
     *
     * Return Value:
     *  - WebDriver instance (initialized by initializeDriver())
     *  - null if driver not initialized
     *
     * Safety:
     *  - No exception throwing
     *  - Callers should check for null before using
     *  - In normal Cucumber flow, driver is always initialized via setUp()
     *
     * @return The active WebDriver instance, or null if not initialized
     */
    public static WebDriver getDriver() {
        // Return the static driver variable
        // Source: Created and populated by initializeDriver()
        // Destination: Returned to caller for browser interaction
        return driver;
    }

    /**
     * Quit the browser and destroy the WebDriver instance.
     *
     * Purpose:
     *  - Close all browser windows
     *  - Terminate WebDriver process and free system resources
     *  - Reset driver to null (prepare for next test scenario's fresh start)
     *  - Clean up gracefully to avoid process leaks
     *
     * Singleton Reset:
     *  - After this method, driver == null
     *  - Next call to initializeDriver() will create fresh WebDriver instance
     *  - Ensures each scenario gets isolated browser instance
     *
     * How It Works:
     *  Step 1: Check if driver exists (not null)
     *     - Prevents NullPointerException
     *     - Only proceeds if driver was initialized
     *
     *  Step 2: Call driver.quit()
     *     - Closes all open browser windows
     *     - Terminates WebDriver process
     *     - Releases system resources (memory, ports, etc.)
     *     - Blocks until all cleanup is complete
     *
     *  Step 3: Set driver to null
     *     - Reset singleton variable to initial state
     *     - Allows next test scenario to create new driver instance
     *     - Prevents accidental reuse of closed driver
     *
     * Where It's Called:
     *  - Hooks.tearDown() (after each scenario)
     *  - Only called after tests complete (passed or failed)
     *
     * Lifecycle:
     *  Before: driver points to active Chrome/Firefox/Edge instance
     *  After: driver = null, all browser windows closed
     *  Next: setUp() will call initializeDriver() again (driver==null check passes)
     *
     * Error Handling:
     *  - No exceptions thrown
     *  - Safe to call even if driver doesn't exist (null check prevents errors)
     *
     * Resource Management:
     *  - Prevents browser process zombies
     *  - Frees up system resources for next scenario
     *  - Essential for test stability in long test suites
     */
    public static void quitDriver() {
        // STEP 1: Check if driver is initialized (not null)
        // Protects against NullPointerException
        if (driver != null) {
            // STEP 2: Close browser and terminate WebDriver process
            // Source: driver instance (created by initializeDriver())
            // Action: Calls driver.quit() which:
            //   - Closes all open browser windows
            //   - Terminates WebDriver server process
            //   - Releases network ports used by driver
            //   - Releases memory allocated to driver
            // Blocking: This call waits until all cleanup is complete
            driver.quit();

            // STEP 3: Reset driver to null
            // Source: driver variable (just quit)
            // Action: Set driver to null
            // Purpose: Reset singleton state for next test scenario
            // Effect: Next call to initializeDriver() will create fresh instance
            driver = null;
        }
        // End of cleanup - driver is now null and all resources freed
    }
}