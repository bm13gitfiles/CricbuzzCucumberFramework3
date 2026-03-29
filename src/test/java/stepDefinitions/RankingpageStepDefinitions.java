package stepDefinitions;

import base.BaseClass;
import constants.Constants;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pageObjects.RankingpageElements;
import webCommons.WebCommons;

import java.util.List;

public class RankingpageStepDefinitions {

    private WebDriver driver = BaseClass.getDriver();
    private RankingpageElements rankingPage = new RankingpageElements(driver);
    private WebCommons commons = new WebCommons(driver);



    // COMMON STEPS


    @Given("The Cricbuzz website is launched")
    public void the_cricbuzz_website_is_launched() {
        commons.launchTheURL(Constants.BASE_URL);
        System.out.println("Cricbuzz homepage launched");
    }

    @Given("The Cricbuzz Mens Ranking page is launched")
    public void the_cricbuzz_mens_ranking_page_is_launched() {
        commons.launchTheURL(Constants.MENS_RANKINGS_URL);
        System.out.println("Men’s Ranking page launched");
    }

    @Given("The Cricbuzz Womens Ranking page is launched")
    public void the_cricbuzz_womens_ranking_page_is_launched() {
        commons.launchTheURL(Constants.WOMENS_RANKINGS_URL);
        System.out.println("Women’s Ranking page launched");
    }


    // NAVIGATION (Header > Rankings)

    @When("User navigates to Mens Ranking page from Header > Rankings > ICC Rankings - Men")
    public void navigate_to_mens_ranking() {
        commons.hoverOverElement(rankingPage.rankingsLink);
        commons.click(rankingPage.iccMensRankingLink);
        System.out.println("Navigated to Men’s ranking page");
    }

    @When("User navigates to Womens Ranking page from Header > Rankings > ICC Rankings - Women")
    public void navigate_to_womens_ranking() {
        commons.hoverOverElement(rankingPage.rankingsLink);
        commons.click(rankingPage.iccWomensRankingLink);
        System.out.println("Navigated to Women’s ranking page");
    }



    // PAGE DISPLAY ASSERTIONS

    @Then("The Men's ranking page should be displayed to the user")
    public void verify_mens_ranking_page_displayed() {
        commons.explicitWait(rankingPage.mensRankingPageTitle);
        System.out.println("Men's Ranking Page is displayed");
    }

    @Then("The Womens ranking page should be displayed to the user")
    public void verify_womens_ranking_page_displayed() {
        commons.explicitWait(rankingPage.womensRankingPageTitle);
        System.out.println("Women’s Ranking Page is displayed");
    }


    // TITLE VALIDATION

    @Then("It should display the Title as {string} in Browser Tab")
    public void verify_browser_tab_title(String expectedTitle) {
        String actual = commons.getTitleOfTheWebPage();

        if (!actual.equals(expectedTitle)) {
            throw new AssertionError("Expected: " + expectedTitle + "\nBut Found: " + actual);
        }
        System.out.println("Title verified successfully: " + actual);
    }


    // MEN — TEST FORMAT

    @Then("By default it should display the list of Test Batters with player points")
    public void verify_test_batters_list_displayed() {
        commons.explicitWait(rankingPage.topBatterNames.get(0));
        System.out.println("Test Batters list displayed");
    }

    @Then("User should able to get Top 10 Test batters with player points")
    public void get_top_10_test_batters() {
        List<String> names = commons.getTextFromElements(rankingPage.topBatterNames);
        List<String> points = commons.getTextFromElements(rankingPage.topBatterPoints);

        System.out.println("\n Top 10 Test Batters:");
        for (int i = 0; i < 10; i++)
            System.out.println((i + 1) + ". " + names.get(i) + " - " + points.get(i));
    }

    @Then("User should be able to get Best Test Batsman")
    public void get_best_test_batsman() {
        System.out.println("\n Best Test Batsman: " +
                rankingPage.topBatterNames.get(0).getText() +
                " (" + rankingPage.topBatterPoints.get(0).getText() + ")");
    }


    // MEN — ODI FORMAT

    @When("User selects the ODI button")
    public void select_odi_button() {
        commons.jsClick(rankingPage.odiSelectorButton);
        System.out.println("ODI format selected");
    }

    @Then("It should display the list of ODI Batters with player points")
    public void verify_odi_batters_list() {
        commons.explicitWait(rankingPage.topBatterNames.get(0));
        System.out.println("ODI Batters list displayed");
    }

    @Then("User should able to get Top 10 ODI batters with player points")
    public void get_top_10_odi_batters() {
        List<String> names = commons.getTextFromElements(rankingPage.topBatterNames);
        List<String> points = commons.getTextFromElements(rankingPage.topBatterPoints);

        System.out.println("\n Top 10 ODI Batters:");
        for (int i = 0; i < 10; i++)
            System.out.println((i + 1) + ". " + names.get(i) + " - " + points.get(i));
    }


    // MEN — T20I FORMAT

    @When("User selects the T20i button")
    public void select_t20i_button() {
        commons.jsClick(rankingPage.t20iSelectorButton);
        System.out.println("T20I format selected");
    }

    @Then("It should display the list of T20i Batters with player points")
    public void verify_t20i_batters_list() {
        commons.explicitWait(rankingPage.topBatterNames.get(0));
        System.out.println("T20I Batters list displayed");
    }

    @Then("User should able to get Top 10 T20i batters with player points")
    public void get_top_10_t20i_batters() {
        List<String> names = commons.getTextFromElements(rankingPage.topBatterNames);
        List<String> points = commons.getTextFromElements(rankingPage.topBatterPoints);

        System.out.println("\n Top 10 T20I Batters:");
        for (int i = 0; i < 10; i++)
            System.out.println((i + 1) + ". " + names.get(i) + " - " + points.get(i));
    }


    // COMMON BEST BATSMAN (Men/Women)
    @Then("User should be able to get Top Batsman")
    public void get_top_batsman() {
        System.out.println("\n Top Batsman: " +
                rankingPage.topBatterNames.get(0).getText() +
                " (" + rankingPage.topBatterPoints.get(0).getText() + ")");
    }


    // WOMEN — ODI FORMAT

    @Then("By default it should display the list of Top ODI Women Batters with player points")
    public void verify_odi_women_list() {
        commons.explicitWait(rankingPage.topBatterNames.get(0));
        System.out.println("ODI Women Batters list displayed");
    }

    @Then("User should able to get Top 10 ODI Women Batters with player points")
    public void get_top_10_odi_women_batters() {
        List<String> names = commons.getTextFromElements(rankingPage.topBatterNames);
        List<String> points = commons.getTextFromElements(rankingPage.topBatterPoints);

        System.out.println("\n Top 10 ODI Women Batters:");
        for (int i = 0; i < 10; i++)
            System.out.println((i + 1) + ". " + names.get(i) + " - " + points.get(i));
    }

    //  WOMEN — T20I FORMAT (MISSING EARLIER — NOW FIXED)


    @Then("It should display the list of Top T20i Women Batters with player points")
    public void verify_t20i_women_list() {
        commons.explicitWait(rankingPage.topBatterNames.get(0));
        System.out.println("T20I Women Batters list displayed");
    }

    @Then("User should able to get Top 10 T20i Women Batters with player points")
    public void get_top_10_t20i_women_batters() {
        List<String> names = commons.getTextFromElements(rankingPage.topBatterNames);
        List<String> points = commons.getTextFromElements(rankingPage.topBatterPoints);

        System.out.println("\n Top 10 T20I Women Batters:");
        for (int i = 0; i < 10; i++)
            System.out.println((i + 1) + ". " + names.get(i) + " - " + points.get(i));
    }


    @Then("User should be able to get Best Batsman")
    public void user_should_be_able_to_get_best_batsman() {

        // wait for first element
        commons.explicitWait(rankingPage.topBatterNames.get(0));

        String name = rankingPage.topBatterNames.get(0).getText();
        String points = rankingPage.topBatterPoints.get(0).getText();

        System.out.println("\n Best Batsman: " + name + " (" + points + " pts)");
    }

}