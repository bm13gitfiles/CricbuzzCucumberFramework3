package constants;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Constants - Centralized configuration management.
 *
 * Purpose:
 *  - Provide application-wide constant values
 *  - Read values from src/main/resources/config.properties
 *  - Offer safe defaults if properties are missing
 *  - Avoid hard-coding sensitive/configurable values
 *
 * Configuration Source:
 *  File: src/main/resources/config.properties
 *  Fallback: Hard-coded defaults in this class
 *  Override: Via system properties if needed
 *
 * Usage:
 *  Constants.BASE_URL → reads cricbuzz.baseUrl from config.properties
 *  Constants.BROWSER → reads cricbuzz.browser from config.properties
 *  Constants.TIMEOUT → reads cricbuzz.explicitWaitSeconds from config.properties
 *
 * Pipeline:
 *  config.properties (static block loads)
 *  → PROPS object populated
 *  → getProperty() methods return values
 *  → Step definitions & page objects use Constants.* values
 */
public class Constants {

    // Logger for debug messages
    private static final Logger LOGGER = Logger.getLogger(Constants.class.getName());

    // Properties loaded from src/main/resources/config.properties
    private static final Properties PROPS = new Properties();

    /**
     * Static initializer block - runs once when Constants class is first loaded.
     *
     * Purpose:
     *  - Load configuration from properties file
     *  - Populate PROPS object for use by getter methods
     *
     * Configuration Source:
     *  File: src/main/resources/config.properties
     *  Location: Compiled into target/classes/config.properties at build time
     *
     * Flow:
     *  1. Try to load config.properties from classpath
     *  2. If found: parse key-value pairs into PROPS map
     *  3. If not found: log warning, continue with defaults
     *  4. If error: log warning, continue with defaults (graceful fallback)
     *
     * Destination:
     *  - PROPS object (in-memory Map<String, String>)
     *  - Accessed by getter methods (e.g., getBrowser(), getTimeout())
     *  - Used throughout the application via Constants.*
     */
    static {
        try (InputStream in = Constants.class.getClassLoader().getResourceAsStream("config.properties")) {
            // Check if config.properties was found on classpath
            if (in != null) {
                // File exists: load properties from input stream
                PROPS.load(in);
                // Log successful load
                LOGGER.info("[Constants] Loaded configuration from config.properties");
            } else {
                // File not found: log warning
                LOGGER.warning("[Constants] config.properties not found on classpath; using default values");
            }
        } catch (Exception e) {
            // Any error during loading: log warning and continue gracefully
            LOGGER.log(Level.WARNING, "[Constants] Error loading config.properties; using default values", e);
            // App does NOT crash; defaults defined below will be used
        }
    }

    // ============================================
    // DEFAULT VALUES (fallback if config.properties not found)
    // ============================================

    private static final String DEFAULT_BROWSER = "chrome";
    private static final int DEFAULT_IMPLICIT_WAIT = 10;
    private static final int DEFAULT_EXPLICIT_WAIT = 30;
    private static final String DEFAULT_BASE_URL = "https://www.cricbuzz.com/";
    private static final String DEFAULT_MENS_RANKINGS_URL = "https://www.cricbuzz.com/cricket-stats/icc-rankings/men/batting";
    private static final String DEFAULT_WOMENS_RANKINGS_URL = "https://www.cricbuzz.com/cricket-stats/icc-rankings/women/batting";
    private static final String DEFAULT_PSL_SERIES_URL = "https://www.cricbuzz.com/cricket-series/11537/pakistan-super-league-2026";
    private static final String DEFAULT_IPL_SERIES_URL = "https://www.cricbuzz.com/cricket-series/9241/Indian-Premier-League-2026";
    private static final int DEFAULT_TOP_PLAYERS_COUNT = 10;

    // ============================================
    // BROWSER & DRIVER CONFIGURATION CONSTANTS
    // ============================================

    /**
     * Browser type to launch (chrome, firefox, edge).
     *
     * Source: config.properties key "cricbuzz.browser"
     * Default: "chrome" if not found in config
     * Used by: BaseClass.initializeDriver() to decide which WebDriver to create
     * Example: "chrome" → new ChromeDriver()
     */
    public static final String BROWSER = getProperty("cricbuzz.browser", DEFAULT_BROWSER);

    /**
     * Implicit wait timeout in seconds.
     *
     * Source: config.properties key "cricbuzz.implicitWaitSeconds"
     * Default: 10 seconds if not found in config
     * Used by: BaseClass.initializeDriver() sets driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS))
     * Effect: WebDriver waits up to this duration before throwing NoSuchElementException on findElement() calls
     */
    public static final int IMPLICIT_WAIT_SECONDS = getPropertyAsInt("cricbuzz.implicitWaitSeconds", DEFAULT_IMPLICIT_WAIT);

    /**
     * Explicit wait timeout in seconds.
     *
     * Source: config.properties key "cricbuzz.explicitWaitSeconds"
     * Default: 30 seconds if not found in config
     * Used by: WebCommons.explicitWait() methods use WebDriverWait with this duration
     * Effect: WebDriverWait waits up to this duration for expected conditions (visibility, clickability, etc.)
     * Note: Different from implicit wait; explicit wait is more precise (waits for specific conditions)
     */
    public static final int TIMEOUT = getPropertyAsInt("cricbuzz.explicitWaitSeconds", DEFAULT_EXPLICIT_WAIT);

    // ============================================
    // APPLICATION URL CONSTANTS
    // ============================================

    /**
     * Base URL for Cricbuzz website.
     *
     * Source: config.properties key "cricbuzz.baseUrl"
     * Default: "https://www.cricbuzz.com/" if not found
     * Used by: WebCommons.launchTheURL(Constants.BASE_URL) in step definitions
     * Example: commons.launchTheURL(Constants.BASE_URL) → browser navigates to base URL
     */
    public static final String BASE_URL = getProperty("cricbuzz.baseUrl", DEFAULT_BASE_URL);

    /**
     * Men's ICC Rankings page URL.
     *
     * Source: config.properties key "cricbuzz.mensRankingsUrl"
     * Default: Rankings page URL if not found
     * Used by: Step definitions testing Men's Rankings functionality
     * Example: commons.launchTheURL(Constants.MENS_RANKINGS_URL)
     */
    public static final String MENS_RANKINGS_URL = getProperty("cricbuzz.mensRankingsUrl", DEFAULT_MENS_RANKINGS_URL);

    /**
     * Women's ICC Rankings page URL.
     *
     * Source: config.properties key "cricbuzz.womensRankingsUrl"
     * Default: Women's Rankings page URL if not found
     * Used by: Step definitions testing Women's Rankings functionality
     * Example: commons.launchTheURL(Constants.WOMENS_RANKINGS_URL)
     */
    public static final String WOMENS_RANKINGS_URL = getProperty("cricbuzz.womensRankingsUrl", DEFAULT_WOMENS_RANKINGS_URL);

    /**
     * Pakistan Super League (PSL) 2026 Series page URL.
     *
     * Source: config.properties key "cricbuzz.pslSeriesUrl"
     * Default: PSL Series URL if not found
     * Used by: PSL-related step definitions and page objects
     * Example: commons.launchTheURL(Constants.PSL_SERIES_URL)
     */
    public static final String PSL_SERIES_URL = getProperty("cricbuzz.pslSeriesUrl", DEFAULT_PSL_SERIES_URL);

    /**
     * Indian Premier League (IPL) 2026 Series page URL.
     *
     * Source: config.properties key "cricbuzz.iplSeriesUrl"
     * Default: IPL Series URL if not found
     * Used by: IPL-related step definitions (IPLSeriesStepDefinitions) and page objects (IPLSeriespageElements)
     * Example: commons.launchTheURL(Constants.IPL_SERIES_URL)
     */
    public static final String IPL_SERIES_URL = getProperty("cricbuzz.iplSeriesUrl", DEFAULT_IPL_SERIES_URL);

    // ============================================
    // TEST DATA CONFIGURATION CONSTANTS
    // ============================================

    /**
     * Number of top players to display in statistics tables.
     *
     * Source: config.properties key "cricbuzz.topPlayersCount"
     * Default: 10 if not found
     * Used by: Step definitions iterating through top batsmen, bowlers, etc.
     * Example: for(int i=0; i<Constants.TOP_PLAYERS_COUNT; i++) { ... }
     * Located in: IPLSeriesStepDefinitions line 211, 260 (hard-coded loops)
     */
    public static final int TOP_PLAYERS_COUNT = getPropertyAsInt("cricbuzz.topPlayersCount", DEFAULT_TOP_PLAYERS_COUNT);

    // ============================================
    // PRIVATE HELPER METHODS
    // ============================================

    /**
     * Get property value from config.properties as String.
     *
     * Purpose:
     *  - Retrieve configuration value safely
     *  - Provide default if key not found
     *  - Trim whitespace from result
     *
     * Flow:
     *  1. Check if PROPS contains the key
     *  2. If found: return value (trimmed)
     *  3. If not found: return default
     *  4. No exception thrown (safe lookup)
     *
     * @param key Configuration key (e.g., "cricbuzz.browser")
     * @param defaultValue Value to return if key not found
     * @return Configuration value or default (never null)
     */
    private static String getProperty(String key, String defaultValue) {
        // Get value from PROPS, or use default if missing
        String value = PROPS.getProperty(key, defaultValue);
        // Trim whitespace and return
        return (value != null) ? value.trim() : defaultValue;
    }

    /**
     * Get property value from config.properties as integer.
     *
     * Purpose:
     *  - Retrieve numeric configuration safely
     *  - Parse string to integer with error handling
     *  - Provide default if key not found or parse fails
     *
     * Flow:
     *  1. Get value from PROPS (as string)
     *  2. Try to parse string to integer
     *  3. If parse fails (e.g., "abc" → NumberFormatException): log warning, use default
     *  4. Return integer value (never null)
     *
     * @param key Configuration key (e.g., "cricbuzz.explicitWaitSeconds")
     * @param defaultValue Value to return if key not found or parse fails
     * @return Configuration value as integer or default
     */
    private static int getPropertyAsInt(String key, int defaultValue) {
        try {
            // Get string value from PROPS
            String value = PROPS.getProperty(key, String.valueOf(defaultValue));
            // Parse string to integer
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            // If parsing fails, log warning and use default
            LOGGER.log(Level.WARNING, "[Constants] Invalid integer for key: " + key + "; using default: " + defaultValue, e);
            return defaultValue;
        }
    }
}