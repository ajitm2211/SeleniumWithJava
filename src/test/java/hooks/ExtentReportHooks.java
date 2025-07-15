package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import com.aventstack.extentreports.Status;
import utils.ExtentManager;

public class ExtentReportHooks {

    @After
    public void afterScenario(Scenario scenario) {
        if (ExtentManager.getTest() != null) {
            if (scenario.isFailed()) {
                ExtentManager.getTest().log(Status.FAIL, "Scenario failed: " + scenario.getName());
            } else {
                ExtentManager.getTest().log(Status.PASS, "Scenario passed: " + scenario.getName());
            }
        } else {
            System.err.println("❗ ExtentTest is null in ExtentReportHooks for scenario: " + scenario.getName());
        }
    }
}
