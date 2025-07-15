package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.testng.Reporter;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ExtentManager;
import utils.ScreenshotUtil;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.nio.file.*;

public class TestHooks {

    private String browser;
    private boolean isApiTest;
    private boolean shouldRunThisScenario;

    @Before
    public void setUp(Scenario scenario) {
        isApiTest = scenario.getSourceTagNames().contains("@API");

        // ✅ Skip extra threads for API
        if (isApiTest) {
            String threadName = Thread.currentThread().getName();
            shouldRunThisScenario = threadName.endsWith("0");

            if (!shouldRunThisScenario) {
                System.out.println("⏩ Skipping API thread: " + threadName);
            }
        } else {
            shouldRunThisScenario = true;
        }

        // ✅ Launch browser only for UI
        if (!isApiTest && shouldRunThisScenario) {
            try {
                browser = Reporter.getCurrentTestResult()
                        .getTestContext().getCurrentXmlTest().getParameter("browser");
            } catch (Exception e) {
                browser = System.getProperty("browser");
            }

            if (browser == null || browser.isEmpty()) {
                browser = "chrome";
            }

            System.out.println("🚀 Launching browser: " + browser + " | Thread: " + Thread.currentThread().getId());
            DriverFactory.initDriver(browser);
        }

        // ✅ Create Extent test (UI and API)
        String reportType = ConfigReader.getReportType().toLowerCase();
        if (reportType.equals("extent") || reportType.equals("both")) {
            String testName = scenario.getName();
            if (!isApiTest) {
                testName += " [" + browser + "]";
            }
            ExtentManager.createTest(testName);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        String reportType = ConfigReader.getReportType().toLowerCase();
        String screenshotMode = ConfigReader.getScreenshotMode().toLowerCase();

        // ✅ Skip tearDown for skipped API threads (no duplicate Allure entries)
        if (isApiTest && !shouldRunThisScenario) {
            return;
        }

        boolean takeScreenshot = !isApiTest && shouldRunThisScenario &&
                (screenshotMode.equals("both") ||
                        (screenshotMode.equals("fail") && scenario.isFailed()));

        String screenshotPath = null;
        byte[] screenshotBytes = null;

        if (takeScreenshot) {
            screenshotPath = ScreenshotUtil.takeScreenshot(scenario.getName());
            System.out.println("📸 Screenshot saved: " + screenshotPath);

            try {
                screenshotBytes = Files.readAllBytes(Paths.get(screenshotPath));
                scenario.attach(screenshotBytes, "image/png", "Screenshot");

                if (reportType.equals("allure") || reportType.equals("both")) {
                    Allure.getLifecycle().addAttachment("Screenshot", "image/png", "png", screenshotBytes);
                }
            } catch (IOException e) {
                System.err.println("❌ Screenshot error: " + e.getMessage());
            }
        }

        // ✅ Extent Report Logging
        if (reportType.equals("extent") || reportType.equals("both")) {
            if (ExtentManager.getTest() != null) {
                try {
                    if (scenario.isFailed()) {
                        ExtentManager.getTest().fail("❌ Scenario Failed: " + scenario.getName());
                    } else {
                        ExtentManager.getTest().pass("✅ Scenario Passed: " + scenario.getName());
                    }

                    if (takeScreenshot && screenshotPath != null) {
                        String relativePath = "screenshots/" + Paths.get(screenshotPath).getFileName();
                        ExtentManager.getTest().addScreenCaptureFromPath(relativePath, "Screenshot");
                    }

                } catch (Exception e) {
                    System.err.println("⚠️ Could not attach screenshot: " + e.getMessage());
                }
            }
        }

        // ✅ Allure API Response Attachment (Only from actual thread)
        if (isApiTest && (reportType.equals("allure") || reportType.equals("both"))) {
            String apiResponse = System.getProperty("api.response");
            if (apiResponse != null && !apiResponse.isEmpty()) {
                Allure.addAttachment("API Response", new ByteArrayInputStream(apiResponse.getBytes()));
            }
        }

        // ✅ Quit UI browser only
        if (!isApiTest && shouldRunThisScenario) {
            DriverFactory.quitDriver();
        }

        if (reportType.equals("extent") || reportType.equals("both")) {
            ExtentManager.removeTest();
        }
    }
}
