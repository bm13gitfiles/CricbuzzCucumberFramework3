package stepDefinitions;

import base.BaseClass;
import constants.Constants;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.IPLSeriespageElements;
import webCommons.WebCommons;

import java.util.List;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

/**
 * IPLSeriesStepDefinitions - Step definitions for IPL Series test scenarios.
 *
 * Purpose:
 *  - Implement step definitions for Gherkin scenarios in CricbuzzIPLSeriesTests.feature
 *  - Interact with IPL pages (navigation, validation, data extraction)
 *  - Log results to Cucumber report
 *
 * Step Definition Categories:
 *  - Navigation: launching browser, navigating to IPL pages
 *  - Top News: validating news section content
 *  - Points Table: extracting and validating team standings
 *  - Statistics: displaying most runs, most wickets
 *  - Squads: displaying teams and captains
 *
 * Lifecycle:
 *  1. Hooks.setUp() initializes WebDriver
 *  2. Each scenario step calls corresponding method in this class
 *  3. Methods use getDriver() to access WebDriver (lazy initialization)
 *  4. Hooks.tearDown() quits WebDriver after scenario
 *
 * WebDriver Access Pattern:
 *  - DO NOT initialize driver in constructor (called before setUp())
 *  - DO initialize driver lazily in each step method via getDriver()
 *  - This ensures driver is only accessed after Hooks.setUp() runs
 */
public class IPLSeriesStepDefinitions {

    private static final Logger LOGGER = Logger.getLogger(IPLSeriesStepDefinitions.class.getName());

    // Scenario object for attaching logs/screenshots to report
    // Injected by Cucumber @Before hook
    private Scenario scenario;

    // Page elements object - initialized lazily via getPageElements()
    private IPLSeriespageElements pageElements;

    // Web commons - initialized lazily via getCommons()
    private WebCommons commons;

    /**
     * Cucumber @Before hook - runs before each scenario.
     *
     * Purpose:
     *  - Capture scenario context for logging/screenshot attachment
     *  - Prepare per-scenario setup
     *
     * Flow:
     *  1. Cucumber calls this method before scenario steps
     *  2. Scenario object injected by framework
     *  3. Stored in instance variable for use in step methods
     *
     * @param scenario Current scenario (injected by Cucumber)
     */
    @io.cucumber.java.Before
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        LOGGER.info("[IPLSeriesStepDefinitions] Starting scenario: " + scenario.getName());
    }

    /**
     * Get WebDriver instance lazily.
     *
     * Purpose:
     *  - Lazy initialization: only called when actually needed
     *  - Ensures driver is ready (initialized by Hooks.setUp())
     *  - Safe to use in step methods
     *
     * When to call:
     *  - When you need direct WebDriver access in a step method
     *  - Example: WebElement element = getDriver().findElement(...)
     *
     * Source:
     *  - BaseClass.getDriver() returns static driver instance
     *  - Driver initialized by Hooks.setUp() before any steps run
     *
     * @return WebDriver instance ready for use
     */
    private WebDriver getDriver() {
        WebDriver driver = BaseClass.getDriver();
        if (driver == null) {
            throw new RuntimeException("[IPLSeriesStepDefinitions] WebDriver is null - Hooks.setUp() may not have run");
        }
        return driver;
    }

    /**
     * Get page elements object lazily.
     *
     * Purpose:
     *  - Lazy initialization of IPLSeriespageElements
     *  - Creates PageFactory-initialized element collection
     *  - Reuses same instance across steps in a scenario
     *
     * When to call:
     *  - Access IPL page element locators
     *  - Example: pageElements.iplSeriesTopNews
     *
     * Source:
     *  - getDriver() returns initialized WebDriver
     *  - IPLSeriespageElements(driver) initializes @FindBy elements
     *
     * @return IPLSeriespageElements instance with all @FindBy elements initialized
     */
    private IPLSeriespageElements getPageElements() {
        if (pageElements == null) {
            pageElements = new IPLSeriespageElements(getDriver());
        }
        return pageElements;
    }

    /**
     * Get WebCommons utility instance lazily.
     *
     * Purpose:
     *  - Lazy initialization of WebCommons helper
     *  - Provides reusable methods (click, wait, getText, etc.)
     *  - Reuses same instance across steps in a scenario
     *
     * When to call:
     *  - Use WebCommons methods (e.g., click, wait, getText)
     *  - Example: commons.click(element), commons.explicitWait(element)
     *
     * Source:
     *  - getDriver() returns initialized WebDriver
     *  - WebCommons(driver) initializes helper with current driver
     *
     * @return WebCommons instance initialized with current driver
     */
    private WebCommons getCommons() {
        if (commons == null) {
            commons = new WebCommons(getDriver());
        }
        return commons;
    }

    /**
     * Log message to Cucumber report.
     *
     * Purpose:
     *  - Attach text logs to HTML report
     *  - Provide evidence of step execution details
     *
     * Flow:
     *  1. Convert message to bytes
     *  2. Attach with "text/plain" MIME type
     *  3. Appears in report under "Log" section
     *
     * @param message Text to log (e.g., "Top batsman: Virat Kohli")
     */
    public void logToReport(String message) {
        scenario.attach(message.getBytes(), "text/plain", "Log");
    }

    // ============================================
    // NAVIGATION STEPS
    // ============================================

    /**
     * Launch Cricbuzz website for IPL tests.
     *
     * Gherkin: Given The Cricbuzz website is launched for IPL
     * Purpose: Navigate to base URL
     * Source: Constants.BASE_URL (from config.properties)
     */
    @Given("The Cricbuzz website is launched for IPL")
    public void the_cricbuzz_website_is_launched_for_ipl() {
        getCommons().launchTheURL(Constants.BASE_URL);
        System.out.println("Cricbuzz homepage launched");
        logToReport("Cricbuzz homepage launched successfully");
    }

    /**
     * Navigate to IPL 2026 series page.
     *
     * Gherkin: When User navigates to the Indian Premier League {int} page from Header > Series > Indian Premier League {int}
     * Purpose: Click Series menu → IPL Series link
     * Steps: Hover → Wait → Click
     */
    @When("User navigates to the Indian Premier League {int} page from Header > Series > Indian Premier League {int}")
    public void user_navigates_to_the_indian_premier_league_page_from_header_series_indian_premier_league(Integer int1, Integer int2) {
        getCommons().hoverOverElement(getPageElements().seriesLinkHeader);
        getCommons().explicitWait(getPageElements().iplSeriesLink);
        getCommons().click(getPageElements().iplSeriesLink);
        System.out.println("Indian Premier League 2026 launched");
        logToReport("Navigated to IPL 2026 series page");
    }

    /**
     * Validate IPL series page title.
     *
     * Gherkin: Then The Indian Premier League {int} page should be displayed to the user
     * Purpose: Assert page title matches expected value
     */
    @Then("The Indian Premier League {int} page should be displayed to the user")
    public void the_indian_premier_league_page_should_be_displayed_to_the_user(Integer int1) {
        getCommons().explicitWait(getPageElements().iplSeriesPageTitle);
        String iplSeriespageExpectedTitle = "Indian Premier League 2026";
        String iplSeriespageActualTitle = getCommons().getText(getPageElements().iplSeriesPageTitle);
        if (!iplSeriespageActualTitle.equals(iplSeriespageExpectedTitle)) {
            throw new AssertionError("Expected: " + iplSeriespageExpectedTitle + "\nBut Found: "
                    + iplSeriespageActualTitle);
        }
        logToReport("IPL Series page validated successfully. Title: " + iplSeriespageActualTitle);
    }

    /**
     * Validate browser tab title.
     *
     * Gherkin: Then It should display the IPL page Title as {string} in Browser Tab
     * Purpose: Assert browser title (tab name) matches expected value
     */
    @Then("It should display the IPL page Title as {string} in Browser Tab")
    public void it_should_display_the_ipl_page_title_as_in_browser_tab(String expectedTitle) {
        String actual = getCommons().getTitleOfTheWebPage();

        if (!actual.equals(expectedTitle)) {
            throw new AssertionError("Expected: " + expectedTitle + "\nBut Found: " + actual);
        }
        System.out.println("Title verified successfully: " + actual);
        logToReport("Browser tab title verified: " + actual);
    }

    // ============================================
    // IPL TOP NEWS SECTION
    // ============================================

    /**
     * Launch IPL series page directly.
     *
     * Gherkin: Given Indian Premier League page is launched
     * Purpose: Navigate directly to IPL series URL
     * Source: Constants.IPL_SERIES_URL (from config.properties)
     */
    @Given("Indian Premier League page is launched")
    public void indian_premier_league_page_is_launched() {
        getCommons().launchTheURL(Constants.IPL_SERIES_URL);
        System.out.println("IPL 2026 page launched");
        logToReport("IPL 2026 series page launched from direct URL");
    }

    /**
     * Click on IPL Top News section.
     *
     * Gherkin: When User selects the IPL Top News section
     * Purpose: Click top news link to display latest news
     */
    @When("User selects the IPL Top News section")
    public void user_selects_the_ipl_top_news_section() {
        getCommons().explicitWait(getPageElements().iplSeriesTopNews);
        getCommons().click(getPageElements().iplSeriesTopNews);
        System.out.println("User selected the Top News section");
        logToReport("IPL Top News section selected");
    }

    /**
     * Validate IPL top story title is displayed.
     *
     * Gherkin: Then IPL top story should be displayed to the user
     * Purpose: Extract and log top story title
     */
    @Then("IPL top story should be displayed to the user")
    public void ipl_top_story_should_be_displayed_to_the_user() {
        getCommons().explicitWait(getPageElements().iplSeriesTopNewsTitle);
        String topStoryTitle = getCommons().getText(getPageElements().iplSeriesTopNewsTitle);
        getCommons().logToCucumberReport(scenario,
                "IPL Top News Title: " + topStoryTitle);
        logToReport("Top story title captured: " + topStoryTitle);
    }

    /**
     * Capture and validate IPL top news image.
     *
     * Gherkin: Then A relevant photograph for the IPL top story should be displayed with a caption
     * Purpose: Attach image screenshot and caption to report
     */
    @Then("A relevant photograph for the IPL top story should be displayed with a caption")
    public void a_relevant_photograph_for_the_ipl_top_story_should_be_displayed_with_a_caption() {
        getCommons().explicitWait(getPageElements().iplSeriesTopNewsImage);

        // Attach screenshot of the element to report
        getCommons().attachElementScreenshotToCucumberReport(
                scenario,
                getPageElements().iplSeriesTopNewsImage,
                "IPL_TopNews_Image"
        );

        String caption = getCommons().getText(getPageElements().iplSeriesTopNewsImageDescription);
        getCommons().logToCucumberReport(scenario,
                "Top IPL News Image Caption: " + caption);
        logToReport("News image and caption captured");
    }

    /**
     * Validate and log first paragraph of top story.
     *
     * Gherkin: Then The first paragraph of the IPL top story should be displayed
     * Purpose: Extract and log opening paragraph text
     */
    @Then("The first paragraph of the IPL top story should be displayed")
    public void the_first_paragraph_of_the_ipl_top_story_should_be_displayed() {
        getCommons().explicitWait(getPageElements().iplSeriesTopNewsIntro);
        String introText = getCommons().getText(getPageElements().iplSeriesTopNewsIntro);
        getCommons().logToCucumberReport(scenario,
                "Top IPL News First Paragraph: " + introText);
        logToReport("First paragraph captured");
    }

    // ============================================
    // IPL POINTS TABLE
    // ============================================

    /**
     * Click on IPL Points Table section.
     *
     * Gherkin: When User selects the IPL Points Table section
     * Purpose: Navigate to points table view
     */
    @When("User selects the IPL Points Table section")
    public void user_selects_the_ipl_points_table_section() {
        getCommons().explicitWait(getPageElements().iplSeriesPointsTableLink);
        getCommons().click(getPageElements().iplSeriesPointsTableLink);
        System.out.println("IPL 2026 Points Table link clicked");
        logToReport("Points Table section selected");
    }

    /**
     * Validate points table and log all 8 teams.
     *
     * Gherkin: Then IPL points table should be displayed to the user
     * Purpose: Extract team names, points, NRR and display top 8 teams
     * Source: Constants.TOP_PLAYERS_COUNT (configurable, default 10)
     */
    @Then("IPL points table should be displayed to the user")
    public void ipl_points_table_should_be_displayed_to_the_user() {
        getCommons().explicitWait(getPageElements().iplSeriesPointsTable);
        List<String> topRankedTeams = getCommons().getTextFromElements
                (getPageElements().iplSeriesPointsTableTeamNames);
        List<String> teamNRR = getCommons().getTextFromElements(getPageElements().iplSeriesPointsTableTeamNRR);
        List<String> teamPoints = getCommons().getTextFromElements(getPageElements().iplSeriesPointsTableTeamPoints);

        StringBuilder sb = new StringBuilder("\nTop IPL teams in playoff contention:\n");
        for (int i = 0; i < 8; i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(topRankedTeams.get(i))
                    .append(" With points - ")
                    .append(teamPoints.get(i))
                    .append(" With NRR - ")
                    .append(teamNRR.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
        logToReport("Points table logged: 8 teams displayed");
    }

    /**
     * Identify and log top team in playoff contention.
     *
     * Gherkin: Then The user should be able to identify the top team in IPL playoff contention
     * Purpose: Extract top 4 teams
     */
    @Then("The user should be able to identify the top team in IPL playoff contention")
    public void the_user_should_be_able_to_identify_the_top_team_in_ipl_playoff_contention() {
        List<String> topRankedTeams = getCommons().getTextFromElements(getPageElements().iplSeriesPointsTableTeamNames);
        List<String> teamNRR = getCommons().getTextFromElements(getPageElements().iplSeriesPointsTableTeamNRR);
        List<String> teamPoints = getCommons().getTextFromElements(getPageElements().iplSeriesPointsTableTeamPoints);

        StringBuilder sb = new StringBuilder("\nTop teams in IPL playoff contention:\n");
        for (int i = 0; i < 4; i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(topRankedTeams.get(i))
                    .append(" With points - ")
                    .append(teamPoints.get(i))
                    .append(" With NRR - ")
                    .append(teamNRR.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
        logToReport("Top 4 teams identified");
    }

    // ============================================
    // IPL STATISTICS - MOST RUNS
    // ============================================

    /**
     * Click on IPL Stats section.
     *
     * Gherkin: When User selects the IPL Stats section
     * Purpose: Navigate to statistics view
     */
    @When("User selects the IPL Stats section")
    public void user_selects_the_ipl_stats_section() {
        getCommons().explicitWait(getPageElements().iplSeriesStatsLink);
        getCommons().click(getPageElements().iplSeriesStatsLink);
        System.out.println("IPL 2026 Stats link clicked");
        logToReport("Stats section selected");
    }

    /**
     * Display IPL most runs statistics.
     *
     * Gherkin: Then The list of IPL batsmen with the most runs should be displayed to the user
     * Purpose: Extract and log top 10 batsmen by runs
     * Source: Constants.TOP_PLAYERS_COUNT (from config.properties)
     */
    @Then("The list of IPL batsmen with the most runs should be displayed to the user")
    public void the_list_of_ipl_batsmen_with_the_most_runs_should_be_displayed_to_the_user() {
        getCommons().explicitWait(getPageElements().iplMostRunsTable);
        System.out.println("IPL 2026 Most Runs Table Loaded");

        List<String> batsmenNames = getCommons().getTextFromElements(getPageElements().iplMostRunsBatsmenNames);
        List<String> batsmenScores = getCommons().getTextFromElements(getPageElements().iplMostRunsBatsmenScores);

        StringBuilder sb = new StringBuilder("\nIPL 2026 Most Runs Table : \n");

        for (int i = 0; i < Constants.TOP_PLAYERS_COUNT; i++) {
            sb.append(batsmenNames.get(i))
                    .append(" With Runs - ")
                    .append(batsmenScores.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
        logToReport("Most runs table logged");
    }

    /**
     * Identify top batsman for Orange Cap.
     *
     * Gherkin: Then The user should be able to identify the top IPL batsman in contention for the Orange Cap
     * Purpose: Log top batsman name and runs
     */
    @Then("The user should be able to identify the top IPL batsman in contention for the Orange Cap")
    public void the_user_should_be_able_to_identify_the_top_ipl_batsman_in_contention_for_the_orange_cap() {
        String topBatsmanName = getPageElements().iplMostRunsBatsmenNames.get(0).getText();
        int topBatsmanScore = parseInt(getPageElements().iplMostRunsBatsmenScores.get(0).getText());
        String logOutput = "\nIPL Orange Cap Contender : " + topBatsmanName + " with Score " + topBatsmanScore;
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
        logToReport("Orange Cap contender identified: " + topBatsmanName);
    }

    // ============================================
    // IPL STATISTICS - MOST WICKETS
    // ============================================

    /**
     * Navigate to IPL Most Wickets statistics.
     *
     * Gherkin: When User selects IPL Stats > Most Wickets
     * Purpose: Click Stats → Most Wickets link
     */
    @When("User selects IPL Stats > Most Wickets")
    public void user_selects_ipl_stats_most_wickets() throws InterruptedException {
        getCommons().explicitWait(getPageElements().iplSeriesStatsLink);
        getCommons().click(getPageElements().iplSeriesStatsLink);
        System.out.println("IPL 2026 Stats link clicked");
        getCommons().explicitWait(getPageElements().iplMostWicketsLink);
        getCommons().scrollToElement(getPageElements().iplMostWicketsLink);
        getCommons().click(getPageElements().iplMostWicketsLink);
        System.out.println("IPL 2026 Stats - Most wickets clicked");
        Thread.sleep(1000);
        logToReport("Most Wickets section selected");
    }

    /**
     * Display IPL most wickets statistics.
     *
     * Gherkin: Then The list of IPL bowlers with the most wickets should be displayed to the user
     * Purpose: Extract and log top 10 bowlers by wickets
     * Source: Constants.TOP_PLAYERS_COUNT (from config.properties)
     */
    @Then("The list of IPL bowlers with the most wickets should be displayed to the user")
    public void the_list_of_ipl_bowlers_with_the_most_wickets_should_be_displayed_to_the_user() {
        getCommons().explicitWait(getPageElements().iplMostWicketsTable);
        System.out.println("IPL 2026 Most Wickets Table Loaded");

        List<String> bowlerNames = getCommons().getTextFromElements(getPageElements().iplMostWicketsBowlerNames);
        List<String> bowlerWickets = getCommons().getTextFromElements(getPageElements().iplMostWickets);

        StringBuilder sb = new StringBuilder("\nIPL 2026 Most Wickets Table : \n");

        for (int i = 0; i < Constants.TOP_PLAYERS_COUNT; i++) {
            sb.append(bowlerNames.get(i))
                    .append(" With Wickets - ")
                    .append(bowlerWickets.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
        logToReport("Most wickets table logged");
    }

    /**
     * Identify top bowler for Purple Cap.
     *
     * Gherkin: Then The user should be able to identify the top IPL bowler in contention for the Purple Cap
     * Purpose: Log top bowler name and wickets
     */
    @Then("The user should be able to identify the top IPL bowler in contention for the Purple Cap")
    public void the_user_should_be_able_to_identify_the_top_ipl_bowler_in_contention_for_the_purple_cap() {
        List<String> bowlerNames = getCommons().getTextFromElements(getPageElements().iplMostWicketsBowlerNames);
        List<String> bowlerWickets = getCommons().getTextFromElements(getPageElements().iplMostWickets);

        StringBuilder sb = new StringBuilder("\nIPL Purple Cap Contender : ");

        sb.append(bowlerNames.get(0)).append(" with wickets ").append(bowlerWickets.get(0));

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
        logToReport("Purple Cap contender identified: " + bowlerNames.get(0));
    }

    // ============================================
    // IPL SQUADS & CAPTAINS
    // ============================================

    /**
     * Click on IPL Squads section.
     *
     * Gherkin: When User selects the IPL Squads section
     * Purpose: Navigate to squads view
     */
    @When("User selects the IPL Squads section")
    public void user_selects_the_ipl_squads_section() {
        getCommons().explicitWait(getPageElements().iplSeriesSquadsLink);
        getCommons().click(getPageElements().iplSeriesSquadsLink);
        System.out.println("IPL 2026 Squads section clicked");
        logToReport("Squads section selected");
    }

    /**
     * Validate IPL teams are displayed.
     *
     * Gherkin: Then The IPL teams should be displayed
     * Purpose: Extract and log all team names
     */
    @Then("The IPL teams should be displayed")
    public void the_ipl_teams_should_be_displayed() {
        getCommons().explicitWait(getPageElements().iplTeamNames.get(0));

        List<String> iplTeamNames = getCommons().getTextFromElements(getPageElements().iplTeamNames);

        StringBuilder sb = new StringBuilder("IPL 2026 Teams displayed : \n");

        for (int i = 0; i < iplTeamNames.size(); i++) {
            sb.append(i + 1).append(". ").append(iplTeamNames.get(i)).append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
        logToReport("Teams list captured: " + iplTeamNames.size() + " teams");
    }

    /**
     * Identify captains for each IPL team.
     *
     * Gherkin: Then The user should be able to identify the captain of each IPL team
     * Purpose: Click each team, extract captain name, log team-captain mapping
     * Implementation: Dynamic XPath with retry logic to handle stale elements
     */
    @Then("The user should be able to identify the captain of each IPL team")
    public void the_user_should_be_able_to_identify_the_captain_of_each_ipl_team() throws InterruptedException {

        By teamLocator = By.xpath("//div[contains(@class,'w-full px-4 py-2 tb:cursor-pointer items-center flex justify-between border-b')]//span[1]");
        By captainLocator = By.xpath("//div[@class='pl-3 tb:text-base']//span[contains(text(),'Captain')]/..");

        // Wait for teams to load
        getCommons().explicitWait(teamLocator);

        int teamCount = getDriver().findElements(teamLocator).size();

        StringBuilder sb = new StringBuilder("IPL Teams and their Captains : \n");

        String previousCaptain = "";

        for (int i = 1; i <= teamCount; i++) {

            // Always re-fetch team element (NO stale)
            By indexedTeam = By.xpath("(" +
                    "//div[contains(@class,'w-full px-4 py-2 tb:cursor-pointer items-center flex justify-between border-b')]//span[1]"
                    + ")[" + i + "]");

            WebElement teamElement = getCommons().findElement(indexedTeam, 10);

            String teamName = teamElement.getText();

            // Scroll + click using commons methods
            getCommons().scrollToElement(teamElement);
            getCommons().jsClick(teamElement);

            // Wait until captain text changes (NO lambda)
            int retry = 0;
            String currentCaptain = "";

            while (retry < 10) {

                WebElement captainElement = getCommons().findElement(captainLocator, 10);
                currentCaptain = captainElement.getText();

                if (!currentCaptain.isEmpty() && !currentCaptain.equals(previousCaptain)) {
                    break;
                }

                getCommons().threadWait(500);
                retry++;
            }

            String captainName = currentCaptain
                    .replace(" (Captain)", "")
                    .trim();

            sb.append(i)
                    .append(". ")
                    .append(teamName)
                    .append(" - Skipper : ")
                    .append(captainName)
                    .append("\n");

            // Update for next iteration
            previousCaptain = currentCaptain;
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
        logToReport("All team captains captured: " + teamCount + " teams");
    }
}