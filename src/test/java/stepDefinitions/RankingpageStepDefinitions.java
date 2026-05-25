package stepDefinitions;

import base.BaseClass;
import constants.Constants;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pageObjects.RankingpageElements;
import webCommons.WebCommons;

import java.util.List;
import java.util.logging.Logger;

/**
 * RankingpageStepDefinitions - Step definitions for ICC Rankings test scenarios.
 *
 * Purpose:
 *  - Implement step definitions for Gherkin scenarios in CricbuzzRankingpageTests.feature
 *  - Interact with Men's and Women's Rankings pages
 *  - Test different formats (Test, ODI, T20I)
 *  - Extract top player rankings and data
 *
 * Step Definition Categories:
 *  - Navigation: launching browser, navigating to rankings pages
 *  - Format Selection: Test, ODI, T20I buttons
 *  - Data Extraction: top 10 batsmen, top batsman, formats
 *
 * Lifecycle:
 *  1. Hooks.setUp() initializes WebDriver
 *  2. Each scenario step calls corresponding method in this class
 *  3. Methods use getDriver() to access WebDriver (lazy initialization)
 *  4. Hooks.tearDown() quits WebDriver after scenario
 *
 * WebDriver Access Pattern:
 *  - DO NOT initialize driver in constructor
 *  - DO initialize driver lazily in step methods via getDriver()
 */
public class RankingpageStepDefinitions {

    private static final Logger LOGGER = Logger.getLogger(RankingpageStepDefinitions.class.getName());

    // Scenario object for attaching logs
    private Scenario scenario;

    // Page elements object - initialized lazily
    private RankingpageElements rankingPage;

    // Web commons - initialized lazily
    private WebCommons commons;

    /**
     * Cucumber @Before hook - runs before each scenario.
     */
    @io.cucumber.java.Before
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        LOGGER.info("[RankingpageStepDefinitions] Starting scenario: " + scenario.getName());
    }

    /**
     * Get WebDriver instance lazily.
     * @return WebDriver instance ready for use
     */
    private WebDriver getDriver() {
        WebDriver driver = BaseClass.getDriver();
        if (driver == null) {
            throw new RuntimeException("[RankingpageStepDefinitions] WebDriver is null - Hooks.setUp() may not have run");
        }
        return driver;
    }

    /**
     * Get page elements object lazily.
     * @return RankingpageElements initialized with current driver
     */
    private RankingpageElements getPageElements() {
        if (rankingPage == null) {
            rankingPage = new RankingpageElements(getDriver());
        }
        return rankingPage;
    }

    /**
     * Get WebCommons utility instance lazily.
     * @return WebCommons initialized with current driver
     */
    private WebCommons getCommons() {
        if (commons == null) {
            commons = new WebCommons(getDriver());
        }
        return commons;
    }

    // ============================================
    // COMMON STEPS
    // ============================================

    /**
     * Launch Cricbuzz base website.
     * Gherkin: Given The Cricbuzz website is launched
     */
    @Given("The Cricbuzz website is launched")
    public void the_cricbuzz_website_is_launched() {
        getCommons().launchTheURL(Constants.BASE_URL);
        System.out.println("Cricbuzz homepage launched");
        LOGGER.info("[RankingpageStepDefinitions] Cricbuzz homepage launched");
    }

    /**
     * Launch Men's Rankings page directly.
     * Gherkin: Given The Cricbuzz Mens Ranking page is launched
     * Source: Constants.MENS_RANKINGS_URL (from config.properties)
     */
    @Given("The Cricbuzz Mens Ranking page is launched")
    public void the_cricbuzz_mens_ranking_page_is_launched() {
        getCommons().launchTheURL(Constants.MENS_RANKINGS_URL);
        System.out.println("Men's Ranking page launched");
        LOGGER.info("[RankingpageStepDefinitions] Men's Ranking page launched");
    }

    /**
     * Launch Women's Rankings page directly.
     * Gherkin: Given The Cricbuzz Womens Ranking page is launched
     * Source: Constants.WOMENS_RANKINGS_URL (from config.properties)
     */
    @Given("The Cricbuzz Womens Ranking page is launched")
    public void the_cricbuzz_womens_ranking_page_is_launched() {
        getCommons().launchTheURL(Constants.WOMENS_RANKINGS_URL);
        System.out.println("Women's Ranking page launched");
        LOGGER.info("[RankingpageStepDefinitions] Women's Ranking page launched");
    }

    // ============================================
    // NAVIGATION STEPS
    // ============================================

    /**
     * Navigate to Men's Rankings via header menu.
     * Gherkin: When User navigates to Mens Ranking page from Header > Rankings > ICC Rankings - Men
     */
    @When("User navigates to Mens Ranking page from Header > Rankings > ICC Rankings - Men")
    public void navigate_to_mens_ranking() {
        getCommons().hoverOverElement(getPageElements().rankingsLink);
        getCommons().click(getPageElements().iccMensRankingLink);
        System.out.println("Navigated to Men's ranking page");
        LOGGER.info("[RankingpageStepDefinitions] Navigated to Men's ranking page");
    }

    /**
     * Navigate to Women's Rankings via header menu.
     * Gherkin: When User navigates to Womens Ranking page from Header > Rankings > ICC Rankings - Women
     */
    @When("User navigates to Womens Ranking page from Header > Rankings > ICC Rankings - Women")
    public void navigate_to_womens_ranking() {
        getCommons().hoverOverElement(getPageElements().rankingsLink);
        getCommons().click(getPageElements().iccWomensRankingLink);
        System.out.println("Navigated to Women's ranking page");
        LOGGER.info("[RankingpageStepDefinitions] Navigated to Women's ranking page");
    }

    // ============================================
    // PAGE DISPLAY ASSERTIONS
    // ============================================

    /**
     * Validate Men's ranking page is displayed.
     * Gherkin: Then The Men's ranking page should be displayed to the user
     */
    @Then("The Men's ranking page should be displayed to the user")
    public void verify_mens_ranking_page_displayed() {
        getCommons().explicitWait(getPageElements().mensRankingPageTitle);
        System.out.println("Men's Ranking Page is displayed");
        LOGGER.info("[RankingpageStepDefinitions] Men's Ranking Page displayed");
    }

    /**
     * Validate Women's ranking page is displayed.
     * Gherkin: Then The Womens ranking page should be displayed to the user
     */
    @Then("The Womens ranking page should be displayed to the user")
    public void verify_womens_ranking_page_displayed() {
        getCommons().explicitWait(getPageElements().womensRankingPageTitle);
        System.out.println("Women's Ranking Page is displayed");
        LOGGER.info("[RankingpageStepDefinitions] Women's Ranking Page displayed");
    }

    // ============================================
    // TITLE VALIDATION
    // ============================================

    /**
     * Validate browser tab title.
     * Gherkin: Then It should display the Title as {string} in Browser Tab
     */
    @Then("It should display the Title as {string} in Browser Tab")
    public void verify_browser_tab_title(String expectedTitle) {
        String actual = getCommons().getTitleOfTheWebPage();
        if (!actual.equals(expectedTitle)) {
            throw new AssertionError("Expected: " + expectedTitle + "\nBut Found: " + actual);
        }
        System.out.println("Title verified successfully: " + actual);
        getCommons().logToCucumberReport(scenario, "Title verified: " + actual);
    }

    // ============================================
    // MEN — TEST FORMAT
    // ============================================

    /**
     * Validate Test format batters list is displayed by default.
     * Gherkin: Then By default it should display the list of Test Batters with player points
     */
    @Then("By default it should display the list of Test Batters with player points")
    public void verify_test_batters_list_displayed() {
        getCommons().explicitWait(getPageElements().topBatterNames.get(0));
        System.out.println("Test Batters list displayed");
        LOGGER.info("[RankingpageStepDefinitions] Test Batters list displayed");
    }

    /**
     * Extract and log top 10 Test batsmen.
     * Gherkin: Then User should able to get Top 10 Test batters with player points
     */
    @Then("User should able to get Top 10 Test batters with player points")
    public void get_top_10_test_batters() {
        List<String> names = getCommons().getTextFromElements(getPageElements().topBatterNames);
        List<String> points = getCommons().getTextFromElements(getPageElements().topBatterPoints);

        StringBuilder sb = new StringBuilder("\nTop 10 Test Batters:\n");
        for (int i = 0; i < 10; i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(names.get(i))
                    .append(" - ")
                    .append(points.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    /**
     * Identify and log best Test batsman.
     * Gherkin: Then User should be able to get Best Test Batsman
     */
    @Then("User should be able to get Best Test Batsman")
    public void get_best_test_batsman() {
        getCommons().explicitWait(getPageElements().topBatterNames.get(0));

        String name = getPageElements().topBatterNames.get(0).getText();
        String points = getPageElements().topBatterPoints.get(0).getText();

        String logOutput = "\nBest Batsman: " + name + " (" + points + " pts)";

        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    // ============================================
    // MEN — ODI FORMAT
    // ============================================

    /**
     * Click ODI format button.
     * Gherkin: When User selects the ODI button
     */
    @When("User selects the ODI button")
    public void select_odi_button() {
        getCommons().jsClick(getPageElements().odiSelectorButton);
        System.out.println("ODI format selected");
        LOGGER.info("[RankingpageStepDefinitions] ODI format selected");
    }

    /**
     * Validate ODI batters list is displayed.
     * Gherkin: Then It should display the list of ODI Batters with player points
     */
    @Then("It should display the list of ODI Batters with player points")
    public void verify_odi_batters_list() {
        getCommons().explicitWait(getPageElements().topBatterNames.get(0));
        System.out.println("ODI Batters list displayed");
        LOGGER.info("[RankingpageStepDefinitions] ODI Batters list displayed");
    }

    /**
     * Extract and log top 10 ODI batsmen.
     * Gherkin: Then User should able to get Top 10 ODI batters with player points
     */
    @Then("User should able to get Top 10 ODI batters with player points")
    public void get_top_10_odi_batters() {
        List<String> names = getCommons().getTextFromElements(getPageElements().topBatterNames);
        List<String> points = getCommons().getTextFromElements(getPageElements().topBatterPoints);
        StringBuilder sb = new StringBuilder("\nTop 10 ODI Batters:\n");
        for (int i = 0; i < 10; i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(names.get(i))
                    .append(" - ")
                    .append(points.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    // ============================================
    // MEN — T20I FORMAT
    // ============================================

    /**
     * Click T20I format button.
     * Gherkin: When User selects the T20i button
     */
    @When("User selects the T20i button")
    public void select_t20i_button() {
        getCommons().jsClick(getPageElements().t20iSelectorButton);
        System.out.println("T20I format selected");
        LOGGER.info("[RankingpageStepDefinitions] T20I format selected");
    }

    /**
     * Validate T20I batters list is displayed.
     * Gherkin: Then It should display the list of T20i Batters with player points
     */
    @Then("It should display the list of T20i Batters with player points")
    public void verify_t20i_batters_list() {
        getCommons().explicitWait(getPageElements().topBatterNames.get(0));
        System.out.println("T20I Batters list displayed");
        LOGGER.info("[RankingpageStepDefinitions] T20I Batters list displayed");
    }

    /**
     * Extract and log top 10 T20I batsmen.
     * Gherkin: Then User should able to get Top 10 T20i batters with player points
     */
    @Then("User should able to get Top 10 T20i batters with player points")
    public void get_top_10_t20i_batters() {
        List<String> names = getCommons().getTextFromElements(getPageElements().topBatterNames);
        List<String> points = getCommons().getTextFromElements(getPageElements().topBatterPoints);

        StringBuilder sb = new StringBuilder("\nTop 10 T20I Batters:\n");
        for (int i = 0; i < 10; i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(names.get(i))
                    .append(" - ")
                    .append(points.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    // ============================================
    // COMMON BEST BATSMAN (Men/Women)
    // ============================================

    /**
     * Identify and log top batsman (generic for men and women).
     * Gherkin: Then User should be able to get Top Batsman
     */
    @Then("User should be able to get Top Batsman")
    public void get_top_batsman() {
        getCommons().explicitWait(getPageElements().topBatterNames.get(0));

        String name = getPageElements().topBatterNames.get(0).getText();
        String points = getPageElements().topBatterPoints.get(0).getText();

        String logOutput = "\nBest Batsman: " + name + " (" + points + " pts)";

        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    // ============================================
    // WOMEN — ODI FORMAT
    // ============================================

    /**
     * Validate ODI Women batters list is displayed by default.
     * Gherkin: Then By default it should display the list of Top ODI Women Batters with player points
     */
    @Then("By default it should display the list of Top ODI Women Batters with player points")
    public void verify_odi_women_list() {
        getCommons().explicitWait(getPageElements().topBatterNames.get(0));
        System.out.println("ODI Women Batters list displayed");
        LOGGER.info("[RankingpageStepDefinitions] ODI Women Batters list displayed");
    }

    /**
     * Extract and log top 10 ODI Women batsmen.
     * Gherkin: Then User should able to get Top 10 ODI Women Batters with player points
     */
    @Then("User should able to get Top 10 ODI Women Batters with player points")
    public void get_top_10_odi_women_batters() {
        List<String> names = getCommons().getTextFromElements(getPageElements().topBatterNames);
        List<String> points = getCommons().getTextFromElements(getPageElements().topBatterPoints);
        StringBuilder sb = new StringBuilder("\nTop 10 ODI Women Batters:\n");
        for (int i = 0; i < 10; i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(names.get(i))
                    .append(" - ")
                    .append(points.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    // ============================================
    // WOMEN — T20I FORMAT
    // ============================================

    /**
     * Validate T20I Women batters list is displayed.
     * Gherkin: Then It should display the list of Top T20i Women Batters with player points
     */
    @Then("It should display the list of Top T20i Women Batters with player points")
    public void verify_t20i_women_list() {
        getCommons().explicitWait(getPageElements().topBatterNames.get(0));
        System.out.println("T20I Women Batters list displayed");
        LOGGER.info("[RankingpageStepDefinitions] T20I Women Batters list displayed");
    }

    /**
     * Extract and log top 10 T20I Women batsmen.
     * Gherkin: Then User should able to get Top 10 T20i Women Batters with player points
     */
    @Then("User should able to get Top 10 T20i Women Batters with player points")
    public void get_top_10_t20i_women_batters() {
        List<String> names = getCommons().getTextFromElements(getPageElements().topBatterNames);
        List<String> points = getCommons().getTextFromElements(getPageElements().topBatterPoints);

        StringBuilder sb = new StringBuilder("\nTop 10 T20I Women Batters:\n");
        for (int i = 0; i < 10; i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(names.get(i))
                    .append(" - ")
                    .append(points.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }

    // ============================================
    // BEST BATSMAN - WOMEN GENERIC
    // ============================================

    /**
     * Identify and log best batsman (women generic).
     * Gherkin: Then User should be able to get Best Batsman
     */
    @Then("User should be able to get Best Batsman")
    public void user_should_be_able_to_get_best_batsman() {
        getCommons().explicitWait(getPageElements().topBatterNames.get(0));

        String name = getPageElements().topBatterNames.get(0).getText();
        String points = getPageElements().topBatterPoints.get(0).getText();

        String logOutput = "\nBest Batsman: " + name + " (" + points + " pts)";

        System.out.println(logOutput);
        getCommons().logToCucumberReport(scenario, logOutput);
    }
}