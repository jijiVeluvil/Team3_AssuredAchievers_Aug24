package runner;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
@RunWith(Cucumber.class)
@CucumberOptions(
		plugin= {"pretty","json:target/cucumber/runner.json","html:target/Team3_AssuredAchievers.html",
				//"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				//"pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
		},
		monochrome=false,
		dryRun=false,
		features= {"/Team3_AssuredAchievers_Aug24/src/test/resources/features/userLoginModule1.feature","/Team3_AssuredAchievers_Aug24/src/test/resources/features/UserLogout.feature"},
		glue={"stepdefinition","hooks","features"}
		)


public class TestRunner  {
	
}
