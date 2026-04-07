package runner;

import base.utilities.EmailUtilities;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-html-report.html",
                "json:target/cucumber.json"
        },
        monochrome = true,
        tags = "@SquadsTests"
)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }


    @AfterSuite
    public void sendEmailReport() {
        try {
            System.out.println("Sending Final Execution Email...");

            String browserUsed = "chrome";
            String executedTag = "@PSLTests";

            EmailUtilities.sendExecutionReport(browserUsed, executedTag);

            System.out.println("Final email sent successfully!");

        } catch (Exception e) {
            System.err.println("Failed to send final email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}