package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@SuppressWarnings("deprecation")
@CucumberOptions(
		features="./APICucumberFramework/features",
		glue = {"stepDefinitions"},
		tags = "@Smoke",
		plugin = "json:target/jsonReports/cucumber-reports.json"	
		)
public class TestRunner {

}
