package stepDefinitions;

import base.BaseClass;
import constants.Constants;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.PSLSeriespageElements;
import webCommons.WebCommons;
import io.cucumber.java.Scenario;

import java.util.List;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

/**
 * PSLSeriesStepDefinitions - Step definitions for PSL Series test scenarios.
 *
 * Purpose:
 *  - Implement step definitions for Gherkin scenarios in CricbuzzPSLSeriesTests.feature
 *  - Interact with PSL pages (navigation, validation, data extraction)
 *  - Log results to Cucumber report
 *
 * Step Definition Categories:
 *  - Navigation: launching browser, navigating to PSL pages
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
public class PSLSeriesStepDefinitions {

    private static final Logger LOGGER = Logger.getLogger(PSLSeriesStepDefinitions.class.getName());

    // Scenario object for attaching logs/screenshots to report
    private Scenario scenario;

    // Page elements object - initialized lazily via getPageElements()
    private PSLSeriespageElements pageElements;

    // Web commons - initialized lazily via getCommons()
    private WebCommons commons;

    /**
     * Cucumber @Before hook - runs before each scenario.
     *
     * Purpose:
     *  - Capture scenario context for logging/screenshot attachment
     */
    @io.cucumber.java.Before
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        LOGGER.info("[PSLSeriesStepDefinitions] Starting scenario: " + scenario.getName());
    }

    /**
     * Get WebDriver instance lazily.
     * Source: BaseClass.getDriver() returns static driver
     * @return WebDriver instance ready for use
     */
    private WebDriver getDriver() {
        WebDriver driver = BaseClass.getDriver();
        if (driver == null) {
            throw new RuntimeException("[PSLSeriesStepDefinitions] WebDriver is null - Hooks.setUp() may not have run");
        }
        return driver;
    }

    /**
     * Get page elements object lazily.
     * Reuses same instance across steps in a scenario
     * @return PSLSeriespageElements initialized with current driver
     */
    private PSLSeriespageElements getPageElements() {
        if (pageElements == null) {
            pageElements = new PSLSeriespageElements(getDriver());
        }
        return pageElements;
    }

    /**
     * Get WebCommons utility instance lazily.
     * Reuses same instance across steps in a scenario
     * @return WebCommons initialized with current driver
     */
    private WebCommons getCommons() {
        if (commons == null) {
            commons = new WebCommons(getDriver());
        }
        return commons;
    }

    // ============================================
    // NAVIGATION STEPS
    // ============================================

    /**
     * Launch Cricbuzz website for PSL tests.
     * Gherkin: Given The Cricbuzz website is launched for PSL
     */
    @Given("The Cricbuzz website is launched for PSL")
    public void the_cricbuzz_website_is_launched_for_PSL() {
        getCommons().launchTheURL(Constants.BASE_URL);
        System.out.println("Cricbuzz homepage launched");
        LOGGER.info("[PSLSeriesStepDefinitions] Cricbuzz homepage launched");
    }

    /**
     * Navigate to PSL 2026 series page.
     * Gherkin: When User navigates to the Pakistan Super League 2026 page from Header > Series > Pakistan Super League 2026
     */
    @When("User navigates to the Pakistan Super League 2026 page from Header > Series > Pakistan Super League 2026")
    public void user_navigates_to_the_psl_page() {
        getCommons().hoverOverElement(getPageElements().seriesLinkHeader);
        getCommons().explicitWait(getPageElements().pslSeriesLink);
        getCommons().click(getPageElements().pslSeriesLink);
        System.out.println("Pakistan Super League 2026 launched");
        LOGGER.info("[PSLSeriesStepDefinitions] Navigated to PSL 2026 series page");
    }

    /**
     * Validate PSL series page title.
     * Gherkin: Then The Pakistan Super League 2026 page should be displayed to the user
     */
    @Then("The Pakistan Super League 2026 page should be displayed to the user")
    public void psl_2026_page_should_be_displayed() {
        getCommons().explicitWait(getPageElements().pslSeriesPageTitle);
        String pslSeriespageExpectedTitle = "Pakistan Super League 2026";
        String pslSeriespageActualTitle = getCommons().getText(getPageElements().pslSeriesPageTitle);
        if (!pslSeriespageActualTitle.equals(pslSeriespageExpectedTitle)) {
            throw new AssertionError("Expected: " + pslSeriespageExpectedTitle + "\nBut Found: " + pslSeriespageActualTitle);
        }
        getCommons().logToCucumberReport(scenario, "PSL Series page validated: " + pslSeriespageActualTitle);
    }

    /**
     * Validate browser tab title.
     * Gherkin: Then It should display the PSL page Title as {string} in Browser Tab
     */
    @Then("It should display the PSL page Title as {string} in Browser Tab")
    public void it_should_display_the_title_in_browser_tab(String expectedTitle) {
        String actual = getCommons().getTitleOfTheWebPage();
        if (!actual.equals(expectedTitle)) {
            throw new AssertionError("Expected: " + expectedTitle + "\nBut Found: " + actual);
        }
        System.out.println("Title verified successfully: " + actual);
        getCommons().logToCucumberReport(scenario, "Browser tab title verified: " + actual);
    }

    // ============================================
    // PSL TOP NEWS SECTION
    // ============================================

    /**
     * Launch PSL series page directly.
     * Gherkin: Given The Cricbuzz - Pakistan Super League 2026 page is launched
     * Source: Constants.PSL_SERIES_URL (from config.properties)
     */
    @Given("The Cricbuzz - Pakistan Super League 2026 page is launched")
    public void the_cricbuzz_psl_page_is_launched() {
        getCommons().launchTheURL(Constants.PSL_SERIES_URL);
        System.out.println("PSL 2026 page launched");
        LOGGER.info("[PSLSeriesStepDefinitions] PSL 2026 page launched from direct URL");
    }

    /**
     * Click on PSL Top News section.
     * Gherkin: When User selects the Top News section
     */
    @When("User selects the Top News section")
    public void user_selects_top_news() {
        getCommons().explicitWait(getPageElements().pslSeriesTopNews);
        getCommons().click(getPageElements().pslSeriesTopNews);
        System.out.println("User selected the Top News section");
        LOGGER.info("[PSLSeriesStepDefinitions] Top News section selected");
    }

    /**
     * Validate PSL top story title is displayed.
     * Gherkin: Then The top story should be displayed to the user
     */
    @Then("The top story should be displayed to the user")
    public void top_story_should_be_displayed() {
        getCommons().explicitWait(getPageElements().pslSeriesTopNewsTitle);
        String topStoryTitle = getCommons().getText(getPageElements().pslSeriesTopNewsTitle);
        getCommons().logToCucumberReport(scenario, "Top News Title: " + topStoryTitle);
    }

    /**
     * Capture and validate PSL top news image.
     * Gherkin: Then A relevant photograph for the top story should be displayed with a caption
     */
    @Then("A relevant photograph for the top story should be displayed with a caption")
    public void photograph_with_caption_should_be_displayed() {
        getCommons().explicitWait(getPageElements().pslSeriesTopNewsImage);
        getCommons().attachElementScreenshotToCucumberReport(
                scenario,
                getPageElements().pslSeriesTopNewsImage,
                "PSL_TopNews_Image"
        );
        String caption = getCommons().getText(getPageElements().pslSeriesTopNewsImageDescription);
        getCommons().logToCucumberReport(scenario, "Top News Image Caption: " + caption);
    }

    /**
     * Validate and log first paragraph of top story.
     * Gherkin: Then The first paragraph of the top story should be displayed
     */
    @Then("The first paragraph of the top story should be displayed")
    public void first_paragraph_of_top_story_should_be_displayed() {
        getCommons().explicitWait(getPageElements().pslSeriesTopNewsIntro);
        String introText = getCommons().getText(getPageElements().pslSeriesTopNewsIntro);
        getCommons().logToCucumberReport(scenario, "Top News First Paragraph: " + introText);
    }

    // ============================================
    // PSL POINTS TABLE
    // ============================================

    /**
     * Click on PSL Points Table section.
     * Gherkin: When User selects the Points Table section
     */
    @When("User selects the Points Table section")
    public void user_selects_points_table_section() {
        getCommons().explicitWait(getPageElements().pslSeriesPointsTableLink);
        getCommons().click(getPageElements().pslSeriesPointsTableLink);
        System.out.println("PSL 2026 Points Table link clicked");
        LOGGER.info("[PSLSeriesStepDefinitions] Points Table section selected");
    }

    /**
     * Validate points table and log all 8 teams.
     * Gherkin: Then The points table should be displayed to the user
     */
    @Then("The points table should be displayed to the user")
    public void points_table_should_be_displayed() {
        getCommons().explicitWait(getPageElements().pslSeriesPointsTable);
        List<String> topRankedTeams = getCommons().getTextFromElements(getPageElements().pslSeriesPointsTableTeamNames);
        List<String> teamNRR = getCommons().getTextFromElements(getPageElements().pslSeriesPointsTableTeamNRR);
        List<String> teamPoints = getCommons().getTextFromElements(getPageElements().pslSeriesPointsTableTeamPoints);

        StringBuilder sb = new StringBuilder("\nTop teams in playoff contention:\n");
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
    }

    /**
     * Identify and log top team in playoff contention.
     * Gherkin: Then The user should be able to identify the top team in playoff contention
     */
    @Then("The user should be able to identify the top team in playoff contention")
    public void user_should_identify_top_team_for_playoffs() {
        List<String> topRankedTeams = getCommons().getTextFromElements(getPageElements().pslSeriesPointsTableTeamNames);
        List<String> teamNRR = getCommons().getTextFromElements(getPageElements().pslSeriesPointsTableTeamNRR);
        List<String> teamPoints = getCommons().getTextFromElements(getPageElements().pslSeriesPointsTableTeamPoints);

        StringBuilder sb = new StringBuilder("\nTop teams in playoff contention:\n");
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
    }

    // ============================================
    // PSL STATISTICS - MOST RUNS
    // ============================================

    /**
     * Click on PSL Stats section.
     * Gherkin: When User selects the Stats section
     */
    @When("User selects the Stats section")
    public void user_selects_stats_section() {
        getCommons().explicitWait(getPageElements().pslSeriesStatsLink);
        getCommons().click(getPageElements().pslSeriesStatsLink);
        System.out.println("PSL 2026 Stats link clicked");
        LOGGER.info("[PSLSeriesStepDefinitions] Stats section selected");
    }

    /**
     * Display PSL most runs statistics.
     * Gherkin: Then The list of batsmen with the most runs should be displayed to the user
     * Source: Constants.TOP_PLAYERS_COUNT (from config.properties)
     */
    @Then("The list of batsmen with the most runs should be displayed to the user")
    public void list_of_batsmen_with_most_runs_should_be_displayed() {
        getCommons().explicitWait(getPageElements().pslMostRunsTable);
        System.out.println("PSL 2026 Most Runs Table Loaded");

        List<String> batsmenNames = getCommons().getTextFromElements(getPageElements().pslMostRunsBatsmenNames);
        List<String> batsmenScores = getCommons().getTextFromElements(getPageElements().pslMostRunsBatsmenScores);

        StringBuilder sb = new StringBuilder("\nPSL 2026 Most Runs Table : \n");

        for (int i = 0; i < Constants.TOP_PLAYERS_COUNT; i++) {
            sb.append(batsmenNames.get(i))
                    .append(" With Runs - ")
                    .append(batsmenScores.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    /**
     * Identify top batsman for Orange Cap.
     * Gherkin: Then The user should be able to identify the top batsman in contention for the Orange Cap
     */
    @Then("The user should be able to identify the top batsman in contention for the Orange Cap")
    public void user_should_identify_orange_cap_contender() {
        String topBatsmanName = getPageElements().pslMostRunsBatsmenNames.get(0).getText();
        int topBatsmanScore = parseInt(getPageElements().pslMostRunsBatsmenScores.get(0).getText());
        String logOutput = "\nOrange Cap Contender : " + topBatsmanName + " with Score " + topBatsmanScore;
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    // ============================================
    // PSL STATISTICS - MOST WICKETS
    // ============================================

    /**
     * Navigate to PSL Most Wickets statistics.
     * Gherkin: When User selects Stats > Most Wickets
     */
    @When("User selects Stats > Most Wickets")
    public void user_selects_most_wickets_section() throws InterruptedException {
        getCommons().explicitWait(getPageElements().pslSeriesStatsLink);
        getCommons().click(getPageElements().pslSeriesStatsLink);
        System.out.println("PSL 2026 Stats link clicked");
        getCommons().explicitWait(getPageElements().pslMostWicketsLink);
        getCommons().scrollToElement(getPageElements().pslMostWicketsLink);
        getCommons().click(getPageElements().pslMostWicketsLink);
        System.out.println("PSL 2026 Stats - Most wickets clicked");
        Thread.sleep(1000);
        LOGGER.info("[PSLSeriesStepDefinitions] Most Wickets section selected");
    }

    /**
     * Display PSL most wickets statistics.
     * Gherkin: Then The list of bowlers with the most wickets should be displayed to the user
     * Source: Constants.TOP_PLAYERS_COUNT (from config.properties)
     */
    @Then("The list of bowlers with the most wickets should be displayed to the user")
    public void list_of_bowlers_with_most_wickets_should_be_displayed() {
        getCommons().explicitWait(getPageElements().pslMostWicketsTable);
        System.out.println("PSL 2026 Most Wickets Table Loaded");

        List<String> bowlerNames = getCommons().getTextFromElements(getPageElements().pslMostWicketsBowlerNames);
        List<String> bowlerWickets = getCommons().getTextFromElements(getPageElements().pslMostWickets);

        StringBuilder sb = new StringBuilder("\nPSL 2026 Most Wickets Table : \n");

        for (int i = 0; i < Constants.TOP_PLAYERS_COUNT; i++) {
            sb.append(bowlerNames.get(i))
                    .append(" With Wickets - ")
                    .append(bowlerWickets.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    /**
     * Identify top bowler for Purple Cap.
     * Gherkin: Then The user should be able to identify the top bowler in contention for the Purple Cap
     */
    @Then("The user should be able to identify the top bowler in contention for the Purple Cap")
    public void user_should_identify_purple_cap_contender() {
        List<String> bowlerNames = getCommons().getTextFromElements(getPageElements().pslMostWicketsBowlerNames);
        List<String> bowlerWickets = getCommons().getTextFromElements(getPageElements().pslMostWickets);

        StringBuilder sb = new StringBuilder("\nPurple Cap Contender : ");
        sb.append(bowlerNames.get(0)).append(" with wickets ").append(bowlerWickets.get(0));

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    // ============================================
    // PSL SQUADS & CAPTAINS
    // ============================================

    /**
     * Click on PSL Squads section.
     * Gherkin: When User selects the Squads section
     */
    @When("User selects the Squads section")
    public void user_selects_squads_section() {
        getCommons().explicitWait(getPageElements().pslSeriesSquadsLink);
        getCommons().click(getPageElements().pslSeriesSquadsLink);
        System.out.println("PSL 2026 Squads section clicked");
        LOGGER.info("[PSLSeriesStepDefinitions] Squads section selected");
    }

    /**
     * Validate PSL teams are displayed.
     * Gherkin: Then The PSL teams should be displayed
     */
    @Then("The PSL teams should be displayed")
    public void psl_teams_should_be_displayed() {
        getCommons().explicitWait(getPageElements().pslTeamNames.get(0));
        List<String> pslTeamNames = getCommons().getTextFromElements(getPageElements().pslTeamNames);

        StringBuilder sb = new StringBuilder("PSL 2026 Teams displayed : \n");
        for (int i = 0; i < pslTeamNames.size(); i++) {
            sb.append(i + 1).append(". ").append(pslTeamNames.get(i)).append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    /**
     * Identify captains for each PSL team.
     * Gherkin: Then The user should be able to identify the captain of each team
     * Implementation: Dynamic XPath with retry logic to handle stale elements
     */
    @Then("The user should be able to identify the captain of each team")
    public void user_should_identify_team_captains() {
        By teamLocator = By.xpath("//div[contains(@class,'w-full px-4 py-2 tb:cursor-pointer items-center flex justify-between border-b')]//span[1]");
        By captainLocator = By.xpath("//div[@class='pl-3 tb:text-base']//span[contains(text(),'Captain')]/..");

        getCommons().explicitWait(teamLocator);

        int teamCount = getDriver().findElements(teamLocator).size();

        StringBuilder sb = new StringBuilder("Teams and their Captains : \n");
        String previousCaptain = "";

        for (int i = 1; i <= teamCount; i++) {
            By indexedTeam = By.xpath("(" +
                    "//div[contains(@class,'w-full px-4 py-2 tb:cursor-pointer items-center flex justify-between border-b')]//span[1]"
                    + ")[" + i + "]");

            WebElement teamElement = getCommons().findElement(indexedTeam, 10);
            String teamName = teamElement.getText();

            getCommons().scrollToElement(teamElement);
            getCommons().jsClick(teamElement);

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

            String captainName = currentCaptain.replace(" (Captain)", "").trim();

            sb.append(i)
                    .append(". ")
                    .append(teamName)
                    .append(" - Skipper : ")
                    .append(captainName)
                    .append("\n");

            previousCaptain = currentCaptain;
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }
}