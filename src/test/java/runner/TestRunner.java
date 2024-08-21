
package runner;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
@RunWith(Cucumber.class)
@CucumberOptions(
		plugin= {"pretty","html:target/Team3_AssuredAchievers.html",
				//"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				//"pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
		},
		monochrome=false,
		dryRun=false,
		features= {"src/test/resources/features/01UserLoginModule1.feature",
				"src/test/resources/features/02DieticianModule.feature",
				"src/test/resources/features/03PatientModule.feature",
				"src/test/resources/features/04Morbidity.feature",
				"src/test/resources/features/05DataCleanUp.feature",
				"src/test/resources/features/06UserLogOut.feature"
				},
		glue={"stepdefinition","hooks"}
		)


public class TestRunner  {
	
}