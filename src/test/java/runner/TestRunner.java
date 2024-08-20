package runner;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty", "html:target/dietician.html",
				"json:target/dietician.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
				}, 
		monochrome=false,
		dryRun=false,
		features= {"src/test/resources/features/01userLoginModule1.feature",
				"src/test/resources/features/02Morbidity.feature",
				"src/test/resources/features/03UserLogout.feature"},
		glue={"stepdefinition","hooks"}
		)


public class TestRunner  {
	
}
