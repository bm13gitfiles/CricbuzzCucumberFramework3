package stepDefinitions;

import base.BaseClass;
import constants.Constants;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObjects.PSLSeriespageElements;
import pageObjects.RankingpageElements;
import webCommons.WebCommons;
import io.cucumber.java.Scenario;

public class PSLSeriesStepDefinitions {



    private WebDriver driver = BaseClass.getDriver();
    private PSLSeriespageElements psl2026PageElements = new PSLSeriespageElements(driver);
    private WebCommons commons = new WebCommons(driver);


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
            commons.launchTheURL(Constants.PSL_Series_URL);
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
            System.out.println("Placeholder");
        }

        @Then("The points table should be displayed to the user")
        public void points_table_should_be_displayed() {
            System.out.println("Placeholder");
        }

        @Then("The user should be able to identify the top team in playoff contention")
        public void user_should_identify_top_team_for_playoffs() {
            System.out.println("Placeholder");
        }


        // PSL Most Runs Statistics


        @When("User selects the Stats section")
        public void user_selects_stats_section() {
            System.out.println("Placeholder");
        }

        @Then("The list of batsmen with the most runs should be displayed to the user")
        public void list_of_batsmen_with_most_runs_should_be_displayed() {
            System.out.println("Placeholder");
        }

        @Then("The user should be able to identify the top batsman in contention for the Orange Cap")
        public void user_should_identify_orange_cap_contender() {
            System.out.println("Placeholder");
        }


        // PSL Most Wickets Statistics

        @When("User selects Stats > Most Wickets")
        public void user_selects_most_wickets_section() {
            System.out.println("Placeholder");
        }

        @Then("The list of bowlers with the most wickets should be displayed to the user")
        public void list_of_bowlers_with_most_wickets_should_be_displayed() {
            System.out.println("Placeholder");
        }

        @Then("The user should be able to identify the top bowler in contention for the Purple Cap")
        public void user_should_identify_purple_cap_contender() {
            System.out.println("Placeholder");
        }

        // PSL Squads & Captains

        @When("User selects the Squads section")
        public void user_selects_squads_section() {
            System.out.println("Placeholder");
        }

        @Then("The PSL teams should be displayed")
        public void psl_teams_should_be_displayed() {
            System.out.println("Placeholder");
        }

        @Then("The user should be able to identify the captain of each team")
        public void user_should_identify_team_captains() {
            System.out.println("Placeholder");
        }

    }


