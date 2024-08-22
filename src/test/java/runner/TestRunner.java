
package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/Team3_AssuredAchievers.html", "pretty",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "pretty",
		"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }, monochrome = false, dryRun = false, features = {
				"src/test/resources/Features" }, glue = { "stepdefinition", "hooks" })

public class TestRunner {

}
