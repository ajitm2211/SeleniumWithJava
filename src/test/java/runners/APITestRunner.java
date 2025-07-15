package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import utils.ExtentManager;

@CucumberOptions(
	    features = "src/test/resources/features",
	    glue = {"stepdefinitions", "hooks"},
	    plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
	    tags = "@API"
	)
	public class APITestRunner extends AbstractTestNGCucumberTests {
	
    @AfterClass(alwaysRun = true)
    public void flushReport() {
        ExtentManager.flush();  // ✅ Flush Extent report
    }
}
