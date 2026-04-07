package stepDefinitions;

import base.BaseClass;
import constants.Constants;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObjects.PSLSeriespageElements;
import pageObjects.RankingpageElements;
import webCommons.WebCommons;
import io.cucumber.java.Scenario;

import java.time.Duration;
import java.util.List;

import static java.lang.Integer.parseInt;

public class PSLSeriesStepDefinitions {



    private final WebDriver driver = BaseClass.getDriver();
    private final PSLSeriespageElements psl2026PageElements = new PSLSeriespageElements(driver);
    private final WebCommons commons = new WebCommons(driver);


    private Scenario scenario;

    @io.cucumber.java.Before
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }



    public void logToReport(Scenario scenario, String message) {
        scenario.attach(message.getBytes(), "text/plain", "Log");
    }




    // Navigation to PSL 2026 Page

        @Given("The Cricbuzz website is launched for PSL")
        public void the_cricbuzz_website_is_launched_for_PSL() {
            commons.launchTheURL(Constants.BASE_URL);
            System.out.println("Cricbuzz homepage launched");
        }

        @When("User navigates to the Pakistan Super League 2026 page from Header > Series > Pakistan Super League 2026")
        public void user_navigates_to_the_psl_page() {
            commons.hoverOverElement(psl2026PageElements.seriesLinkHeader);
            commons.explicitWait(psl2026PageElements.pslSeriesLink);
            commons.click(psl2026PageElements.pslSeriesLink);
            System.out.println("Pakistan Super League 2026 launched");

        }

        @Then("The Pakistan Super League 2026 page should be displayed to the user")
        public void psl_2026_page_should_be_displayed() {
            commons.explicitWait(psl2026PageElements.pslSeriesPageTitle);
            String pslSeriespageExpectedTitle = "Pakistan Super League 2026";
            String pslSeriespageActualTitle = commons.getText(psl2026PageElements.pslSeriesPageTitle);
            if (!pslSeriespageActualTitle.equals(pslSeriespageExpectedTitle)) {
                throw new AssertionError("Expected: " + pslSeriespageExpectedTitle + "\nBut Found: " + pslSeriespageActualTitle);
            }
        }

        @Then("It should display the PSL page Title as {string} in Browser Tab")
        public void it_should_display_the_title_in_browser_tab(String expectedTitle) {
            String actual = commons.getTitleOfTheWebPage();

            if (!actual.equals(expectedTitle)) {
                throw new AssertionError("Expected: " + expectedTitle + "\nBut Found: " + actual);
            }
            System.out.println("Title verified successfully: " + actual);
        }

        //PSL Top News Section


    // PSL Top News Section

    @Given("The Cricbuzz - Pakistan Super League 2026 page is launched")
    public void the_cricbuzz_psl_page_is_launched() {
        commons.launchTheURL(Constants.PSL_Series_URL);
        System.out.println("PSL 2026 page launched");
    }

    @When("User selects the Top News section")
    public void user_selects_top_news() {
        commons.explicitWait(psl2026PageElements.pslSeriesTopNews);
        commons.click(psl2026PageElements.pslSeriesTopNews);

        System.out.println("User selected the Top News section");


    }

    @Then("The top story should be displayed to the user")
    public void top_story_should_be_displayed() {
        commons.explicitWait(psl2026PageElements.pslSeriesTopNewsTitle);

        String topStoryTitle = commons.getText(psl2026PageElements.pslSeriesTopNewsTitle);

        commons.logToCucumberReport(scenario,
                "Top News Title: " + topStoryTitle);
    }

    @Then("A relevant photograph for the top story should be displayed with a caption")
    public void photograph_with_caption_should_be_displayed() {

        commons.explicitWait(psl2026PageElements.pslSeriesTopNewsImage);

        // Attach screenshot of the element
        commons.attachElementScreenshotToCucumberReport(
                scenario,
                psl2026PageElements.pslSeriesTopNewsImage,
                "PSL_TopNews_Image"
        );

        String caption = commons.getText(psl2026PageElements.pslSeriesTopNewsImageDescription);

        commons.logToCucumberReport(scenario,
                "Top News Image Caption: " + caption);
    }

    @Then("The first paragraph of the top story should be displayed")
    public void first_paragraph_of_top_story_should_be_displayed() {

        commons.explicitWait(psl2026PageElements.pslSeriesTopNewsIntro);

        String introText = commons.getText(psl2026PageElements.pslSeriesTopNewsIntro);

        commons.logToCucumberReport(scenario,
                "Top News First Paragraph: " + introText);
    }





        //  PSL Points Table

        @When("User selects the Points Table section")
        public void user_selects_points_table_section() {

            commons.explicitWait(psl2026PageElements.pslSeriesPointsTableLink);
            commons.click(psl2026PageElements.pslSeriesPointsTableLink);
            System.out.println("PSL 2026 Points Table link clicked");

        }

        @Then("The points table should be displayed to the user")
        public void points_table_should_be_displayed() {
            commons.explicitWait(psl2026PageElements.pslSeriesPointsTable);
            List<String>topRankedTeams =  commons.getTextFromElements(psl2026PageElements.pslSeriesPointsTableTeamNames);
            List<String>teamNRR = commons.getTextFromElements(psl2026PageElements.pslSeriesPointsTableTeamNRR);
            List<String>teamPoints = commons.getTextFromElements(psl2026PageElements.pslSeriesPointsTableTeamPoints);

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
            commons.logToCucumberReport(scenario, logOutput);
        }

        @Then("The user should be able to identify the top team in playoff contention")
        public void user_should_identify_top_team_for_playoffs() {
            List<String>topRankedTeams =  commons.getTextFromElements(psl2026PageElements.pslSeriesPointsTableTeamNames);
            List<String>teamNRR = commons.getTextFromElements(psl2026PageElements.pslSeriesPointsTableTeamNRR);
            List<String>teamPoints = commons.getTextFromElements(psl2026PageElements.pslSeriesPointsTableTeamPoints);

            StringBuilder sb = new StringBuilder("\nTop teams in playoff contention:\n");
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


        // PSL Most Runs Statistics


        @When("User selects the Stats section")
        public void user_selects_stats_section() {

            commons.explicitWait(psl2026PageElements.pslSeriesStatsLink);
            commons.click(psl2026PageElements.pslSeriesStatsLink);
            System.out.println("PSL 2026 Stats link clicked");

        }

        @Then("The list of batsmen with the most runs should be displayed to the user")
        public void list_of_batsmen_with_most_runs_should_be_displayed() {
            commons.explicitWait(psl2026PageElements.pslMostRunsTable);
            System.out.println("PSL 2026 Most Runs Table Loaded");

            List<String> batsmenNames = commons.getTextFromElements(psl2026PageElements.pslMostRunsBatsmenNames);
            List<String> batsmenScores = commons.getTextFromElements(psl2026PageElements.pslMostRunsBatsmenScores);

            StringBuffer sb = new StringBuffer("\nPSL 2026 Most Runs Table : \n");

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

        @Then("The user should be able to identify the top batsman in contention for the Orange Cap")
        public void user_should_identify_orange_cap_contender() {

            String topBatsmanName = psl2026PageElements.pslMostRunsBatsmenNames.get(0).getText();
            int topBatsmanScore = parseInt(psl2026PageElements.pslMostRunsBatsmenScores.get(0).getText());
            String logOutput = "\nOrange Cap Contender : "+topBatsmanName + " with Score " +topBatsmanScore;
            System.out.println(logOutput);
            commons.logToCucumberReport(scenario, logOutput);

        }


        // PSL Most Wickets Statistics

        @When("User selects Stats > Most Wickets")
        public void user_selects_most_wickets_section() throws InterruptedException {

            commons.explicitWait(psl2026PageElements.pslSeriesStatsLink);
            commons.click(psl2026PageElements.pslSeriesStatsLink);
            System.out.println("PSL 2026 Stats link clicked");
            commons.explicitWait(psl2026PageElements.pslMostWicketsLink);
            commons.scrollToElement(psl2026PageElements.pslMostWicketsLink);
            commons.click(psl2026PageElements.pslMostWicketsLink);
            System.out.println("PSL 2026 Stats - Most wickets clicked");
            Thread.sleep(1000);

        }

        @Then("The list of bowlers with the most wickets should be displayed to the user")
        public void list_of_bowlers_with_most_wickets_should_be_displayed() {

        commons.explicitWait(psl2026PageElements.pslMostWicketsTable);

        System.out.println("PSL 2026 Most Wickets Table Loaded");

            List<String> bowlerNames = commons.getTextFromElements(psl2026PageElements.pslMostWicketsBowlerNames);
            List<String> bowlerWickets = commons.getTextFromElements(psl2026PageElements.pslMostWickets);

            StringBuffer sb = new StringBuffer("\nPSL 2026 Most Wickets Table : \n");

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

        @Then("The user should be able to identify the top bowler in contention for the Purple Cap")
        public void user_should_identify_purple_cap_contender() {
            List<String> bowlerNames = commons.getTextFromElements(psl2026PageElements.pslMostWicketsBowlerNames);
            List<String> bowlerWickets = commons.getTextFromElements(psl2026PageElements.pslMostWickets);

            StringBuffer sb = new StringBuffer("\nPurple Cap Contender : ");

            sb.append(bowlerNames.get(0)).append(" with wickets ").append(bowlerWickets.get(0));

            String logOutput = sb.toString();
            System.out.println(logOutput);
            commons.logToCucumberReport(scenario, logOutput);

        }

        // PSL Squads & Captains

        @When("User selects the Squads section")
        public void user_selects_squads_section() {

            commons.explicitWait(psl2026PageElements.pslSeriesSquadsLink);
            commons.click(psl2026PageElements.pslSeriesSquadsLink);
            System.out.println("PSL 2026 Points Table link clicked");


        }

        @Then("The PSL teams should be displayed")
        public void psl_teams_should_be_displayed() {

        commons.explicitWait(psl2026PageElements.pslTeamNames.get(0));

        List<String>pslTeamNames = commons.getTextFromElements(psl2026PageElements.pslTeamNames);

        StringBuffer sb = new StringBuffer("PSL 2026 Points team names displayed : \n");

        for (int i=0; i<8; i++){
            sb.append(i+1).append(". ").append(pslTeamNames.get(i)).append("\n");
        }

            String logOutput = sb.toString();
            System.out.println(logOutput);
            commons.logToCucumberReport(scenario, logOutput);

        }

    @Then("The user should be able to identify the captain of each team")
    public void user_should_identify_team_captains() {

        By teamLocator = By.xpath("//div[contains(@class,'w-full px-4 py-2 tb:cursor-pointer items-center flex justify-between border-b')]//span[1]");
        By captainLocator = By.xpath("//div[@class='pl-3 tb:text-base']//span[contains(text(),'Captain')]/..");

        // Wait for teams to load
        commons.explicitWait(teamLocator);

        int teamCount = driver.findElements(teamLocator).size();

        StringBuilder sb = new StringBuilder("Teams and their Captains : \n");

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


