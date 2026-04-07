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

import static java.lang.Integer.parseInt;

public class IPLSeriesStepDefinitions {
    private final WebDriver driver = BaseClass.getDriver();
    private final IPLSeriespageElements ipl2026PageElements = new IPLSeriespageElements(driver);
    private final WebCommons commons = new WebCommons(driver);

    private Scenario scenario;

    @io.cucumber.java.Before
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }


    public void logToReport(Scenario scenario, String message) {
        scenario.attach(message.getBytes(), "text/plain", "Log");
    }

    // Navigation to IPL 2026 Page

    @Given("The Cricbuzz website is launched for IPL")
    public void the_cricbuzz_website_is_launched_for_ipl() {
        commons.launchTheURL(Constants.BASE_URL);
        System.out.println("Cricbuzz homepage launched");
    }

    @When("User navigates to the Indian Premier League {int} page from Header > Series > Indian Premier League {int}")
    public void user_navigates_to_the_indian_premier_league_page_from_header_series_indian_premier_league(Integer int1, Integer int2) {
        commons.hoverOverElement(ipl2026PageElements.seriesLinkHeader);
        commons.explicitWait(ipl2026PageElements.iplSeriesLink);
        commons.click(ipl2026PageElements.iplSeriesLink);
        System.out.println("Indian Premier League 2026 launched");
    }

    @Then("The Indian Premier League {int} page should be displayed to the user")
    public void the_indian_premier_league_page_should_be_displayed_to_the_user(Integer int1) {
        commons.explicitWait(ipl2026PageElements.iplSeriesPageTitle);
        String iplSeriespageExpectedTitle = "Indian Premier League 2026";
        String iplSeriespageActualTitle = commons.getText(ipl2026PageElements.iplSeriesPageTitle);
        if (!iplSeriespageActualTitle.equals(iplSeriespageExpectedTitle)) {
            throw new AssertionError("Expected: " + iplSeriespageExpectedTitle + "\nBut Found: "
                    + iplSeriespageActualTitle);
        }
    }
    @Then("It should display the IPL page Title as {string} in Browser Tab")
    public void it_should_display_the_ipl_page_title_as_in_browser_tab(String expectedTitle) {
        String actual = commons.getTitleOfTheWebPage();

        if (!actual.equals(expectedTitle)) {
            throw new AssertionError("Expected: " + expectedTitle + "\nBut Found: " + actual);
        }
        System.out.println("Title verified successfully: " + actual);
    }

    //IPL Top News Section

    @Given("Indian Premier League page is launched")
    public void indian_premier_league_page_is_launched() {
        commons.launchTheURL(Constants.IPL_Series_URL);
        System.out.println("IPL 2026 page launched");
    }

    @When("User selects the IPL Top News section")
    public void user_selects_the_ipl_top_news_section() {

            commons.explicitWait(ipl2026PageElements.iplSeriesTopNews);
            commons.click(ipl2026PageElements.iplSeriesTopNews);

            System.out.println("User selected the Top News section");
            }

    @Then("IPL top story should be displayed to the user")
    public void ipl_top_story_should_be_displayed_to_the_user() {
        commons.explicitWait(ipl2026PageElements.iplSeriesTopNewsTitle);

        String topStoryTitle = commons.getText(ipl2026PageElements.iplSeriesTopNewsTitle);

        commons.logToCucumberReport(scenario,
                "IPL Top News Title: " + topStoryTitle);
    }

    @Then("A relevant photograph for the IPL top story should be displayed with a caption")
    public void a_relevant_photograph_for_the_ipl_top_story_should_be_displayed_with_a_caption() {

        commons.explicitWait(ipl2026PageElements.iplSeriesTopNewsImage);

        // Attach screenshot of the element
        commons.attachElementScreenshotToCucumberReport(
                scenario,
                ipl2026PageElements.iplSeriesTopNewsImage,
                "IPL_TopNews_Image"
        );

        String caption = commons.getText(ipl2026PageElements.iplSeriesTopNewsImageDescription);

        commons.logToCucumberReport(scenario,
                "Top IPL News Image Caption: " + caption);
    }

    @Then("The first paragraph of the IPL top story should be displayed")
    public void the_first_paragraph_of_the_ipl_top_story_should_be_displayed() {
        commons.explicitWait(ipl2026PageElements.iplSeriesTopNewsIntro);

        String introText = commons.getText(ipl2026PageElements.iplSeriesTopNewsIntro);

        commons.logToCucumberReport(scenario,
                "Top IPL News First Paragraph: " + introText);
    }


//  IPL Points Table


    @When("User selects the IPL Points Table section")
    public void user_selects_the_ipl_points_table_section() {

            commons.explicitWait(ipl2026PageElements.iplSeriesPointsTableLink);
            commons.click(ipl2026PageElements.iplSeriesPointsTableLink);
            System.out.println("IPL 2026 Points Table link clicked");
            }

    @Then("IPL points table should be displayed to the user")
    public void ipl_points_table_should_be_displayed_to_the_user() {
        commons.explicitWait(ipl2026PageElements.iplSeriesPointsTable);
        List<String> topRankedTeams =  commons.getTextFromElements
                (ipl2026PageElements.iplSeriesPointsTableTeamNames);
        List<String>teamNRR = commons.getTextFromElements(ipl2026PageElements.iplSeriesPointsTableTeamNRR);
        List<String>teamPoints = commons.getTextFromElements(ipl2026PageElements.iplSeriesPointsTableTeamPoints);

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
        commons.logToCucumberReport(scenario, logOutput);
    }

    @Then("The user should be able to identify the top team in IPL playoff contention")
    public void the_user_should_be_able_to_identify_the_top_team_in_ipl_playoff_contention() {
        List<String>topRankedTeams =  commons.getTextFromElements(ipl2026PageElements.iplSeriesPointsTableTeamNames);
        List<String>teamNRR = commons.getTextFromElements(ipl2026PageElements.iplSeriesPointsTableTeamNRR);
        List<String>teamPoints = commons.getTextFromElements(ipl2026PageElements.iplSeriesPointsTableTeamPoints);

        StringBuilder sb = new StringBuilder("\nTop teams in IPL playoff contention:\n");
        for (int i = 0; i < 4; i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(topRankedTeams.get(i))
                    .append("With points - ")
                    .append(teamPoints.get(i))
                    .append(" With NRR - ")
                    .append(teamNRR.get(i))
                    .append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        commons.logToCucumberReport(scenario, logOutput);
    }

    // IPL Most Runs Statistics


    @When("User selects the IPL Stats section")
    public void user_selects_the_ipl_stats_section() {

            commons.explicitWait(ipl2026PageElements.iplSeriesStatsLink);
            commons.click(ipl2026PageElements.iplSeriesStatsLink);
            System.out.println("IPL 2026 Stats link clicked");

        }


    @Then("The list of IPL batsmen with the most runs should be displayed to the user")
    public void the_list_of_ipl_batsmen_with_the_most_runs_should_be_displayed_to_the_user() {
        commons.explicitWait(ipl2026PageElements.iplMostRunsTable);
        System.out.println("IPL 2026 Most Runs Table Loaded");

        List<String> batsmenNames = commons.getTextFromElements(ipl2026PageElements.iplMostRunsBatsmenNames);
        List<String> batsmenScores = commons.getTextFromElements(ipl2026PageElements.iplMostRunsBatsmenScores);

        StringBuffer sb = new StringBuffer("\nIPL 2026 Most Runs Table : \n");

        for(int i=0; i<10; i++){
            sb.append(batsmenNames.get(i))
                    .append(" With Runs - ")
                    .append(batsmenScores.get(i))
                    .append("\n");

        }

        String logOutput = sb.toString();

        System.out.println(logOutput);
        commons.logToCucumberReport(scenario, logOutput);
    }

    @Then("The user should be able to identify the top IPL batsman in contention for the Orange Cap")
    public void the_user_should_be_able_to_identify_the_top_ipl_batsman_in_contention_for_the_orange_cap() {
        String topBatsmanName = ipl2026PageElements.iplMostRunsBatsmenNames.get(0).getText();
        int topBatsmanScore = parseInt(ipl2026PageElements.iplMostRunsBatsmenScores.get(0).getText());
        String logOutput = "\nIPL Orange Cap Contender : "+topBatsmanName + " with Score " +topBatsmanScore;
        System.out.println(logOutput);
        commons.logToCucumberReport(scenario, logOutput);
    }

    // IPL Most Wickets Statistics


    @When("User selects IPL Stats > Most Wickets")
    public void user_selects_ipl_stats_most_wickets() throws InterruptedException {
            commons.explicitWait(ipl2026PageElements.iplSeriesStatsLink);
            commons.click(ipl2026PageElements.iplSeriesStatsLink);
            System.out.println("IPL 2026 Stats link clicked");
            commons.explicitWait(ipl2026PageElements.iplMostWicketsLink);
            commons.scrollToElement(ipl2026PageElements.iplMostWicketsLink);
            commons.click(ipl2026PageElements.iplMostWicketsLink);
            System.out.println("IPL 2026 Stats - Most wickets clicked");
            Thread.sleep(1000);
      }

    @Then("The list of IPL bowlers with the most wickets should be displayed to the user")
    public void the_list_of_ipl_bowlers_with_the_most_wickets_should_be_displayed_to_the_user() {
        commons.explicitWait(ipl2026PageElements.iplMostWicketsTable);

        System.out.println("IPL 2026 Most Wickets Table Loaded");

        List<String> bowlerNames = commons.getTextFromElements(ipl2026PageElements.iplMostWicketsBowlerNames);
        List<String> bowlerWickets = commons.getTextFromElements(ipl2026PageElements.iplMostWickets);

        StringBuffer sb = new StringBuffer("\nIPL 2026 Most Wickets Table : \n");

        for(int i=0; i<10; i++){
            sb.append(bowlerNames.get(i))
                    .append(" With Wickets - ")
                    .append(bowlerWickets.get(i))
                    .append("\n");

        }

        String logOutput = sb.toString();

        System.out.println(logOutput);
        commons.logToCucumberReport(scenario, logOutput);
    }

    @Then("The user should be able to identify the top IPL bowler in contention for the Purple Cap")
    public void the_user_should_be_able_to_identify_the_top_ipl_bowler_in_contention_for_the_purple_cap() {
        List<String> bowlerNames = commons.getTextFromElements(ipl2026PageElements.iplMostWicketsBowlerNames);
        List<String> bowlerWickets = commons.getTextFromElements(ipl2026PageElements.iplMostWickets);

        StringBuffer sb = new StringBuffer("\nIPL Purple Cap Contender : ");

        sb.append(bowlerNames.get(0)).append(" with wickets ").append(bowlerWickets.get(0));

        String logOutput = sb.toString();
        System.out.println(logOutput);
        commons.logToCucumberReport(scenario, logOutput);
    }

// IPL Squads & Captains


    @When("User selects the IPL Squads section")
    public void user_selects_the_ipl_squads_section() {

        commons.explicitWait(ipl2026PageElements.iplSeriesSquadsLink);
        commons.click(ipl2026PageElements.iplSeriesSquadsLink);
        System.out.println("IPL 2026 Points Table link clicked");
    }

    @Then("The IPL teams should be displayed")
    public void the_ipl_teams_should_be_displayed() {
        commons.explicitWait(ipl2026PageElements.iplTeamNames.get(0));

        List<String>iplTeamNames = commons.getTextFromElements(ipl2026PageElements.iplTeamNames);

        StringBuffer sb = new StringBuffer("IPL 2026 Points team names displayed : \n");

        for (int i=0; i<8; i++){
            sb.append(i+1).append(". ").append(iplTeamNames.get(i)).append("\n");
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        commons.logToCucumberReport(scenario, logOutput);

    }


    @Then("The user should be able to identify the captain of each IPL team")
    public void the_user_should_be_able_to_identify_the_captain_of_each_ipl_team() throws InterruptedException {


        By teamLocator = By.xpath("//div[contains(@class,'w-full px-4 py-2 tb:cursor-pointer items-center flex justify-between border-b')]//span[1]");
        By captainLocator = By.xpath("//div[@class='pl-3 tb:text-base']//span[contains(text(),'Captain')]/..");

        // Wait for teams to load
        commons.explicitWait(teamLocator);

        int teamCount = driver.findElements(teamLocator).size();

        StringBuilder sb = new StringBuilder("IPL Teams and their Captains : \n");

        String previousCaptain = "";

        for (int i = 1; i <= teamCount; i++) {

            // Always re-fetch team element (NO stale)
            By indexedTeam = By.xpath("(" +
                    "//div[contains(@class,'w-full px-4 py-2 tb:cursor-pointer items-center flex justify-between border-b')]//span[1]"
                    + ")[" + i + "]");

            WebElement teamElement = commons.findElement(indexedTeam, 10);

            String teamName = teamElement.getText();

            // Scroll + click using your methods
            commons.scrollToElement(teamElement);
            commons.jsClick(teamElement);

            // Wait until captain text changes (NO lambda)
            int retry = 0;
            String currentCaptain = "";

            while (retry < 10) {

                WebElement captainElement = commons.findElement(captainLocator, 10);
                currentCaptain = captainElement.getText();

                if (!currentCaptain.isEmpty() && !currentCaptain.equals(previousCaptain)) {
                    break;
                }

                commons.threadWait(500); // using your wait method
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

            // update for next iteration
            previousCaptain = currentCaptain;
        }

        String logOutput = sb.toString();
        System.out.println(logOutput);
        commons.logToCucumberReport(scenario, logOutput);
    }
}