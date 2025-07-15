package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import utils.ExtentManager;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepdefinitions", "hooks"},
    plugin = {
        "pretty",
         "html:target/cucumber-html-report.html",
        "json:target/cucumber-report.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"  // 🔗 Required for Allure
    },
    	    tags = "@UI",
    monochrome = true
)
public class UITestRunner extends AbstractTestNGCucumberTests {

    @DataProvider(parallel = true)
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterClass(alwaysRun = true)
    public void flushReport() {
        ExtentManager.flush();  // ✅ Flush Extent report
    }
}
