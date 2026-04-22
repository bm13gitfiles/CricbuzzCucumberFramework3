package stepDefinitions;

import base.BaseClass;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public void setUp() {
        System.out.println("Initializing the browser...");
        BaseClass.initializeDriver("chrome");
        System.out.println("Browser launched successfully");
    }

    // First @After: Capture screenshot if scenario fails
    @After(order = 1)
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                final byte[] screenshot =
                        ((TakesScreenshot) BaseClass.getDriver()).getScreenshotAs(OutputType.BYTES);

                scenario.attach(screenshot, "image/png", "Failure Screenshot");

                System.out.println("Screenshot taken for failed scenario: " + scenario.getName());
            } catch (Exception e) {
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    // Second @After: Quit driver
    @After(order = 0)
    public void tearDown() {
        System.out.println("Closing the browser...");
        BaseClass.quitDriver();
        System.out.println("Driver quit successfully");
    }




}