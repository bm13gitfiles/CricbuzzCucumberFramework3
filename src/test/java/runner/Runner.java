package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
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
       tags = "@CaptainTestIPL"
)
public class Runner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}