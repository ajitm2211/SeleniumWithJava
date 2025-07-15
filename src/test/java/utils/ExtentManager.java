package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static final ExtentReports extent = new ExtentReports();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    static {
        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
        reporter.config().setDocumentTitle("Automation Test Report");
        reporter.config().setReportName("Extent Report");
        extent.attachReporter(reporter);
    }

    public static ExtentReports getExtentReports() {
        return extent;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    public static void createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        setTest(test);
    }

    public static void flush() {
        extent.flush(); // ✅ generates the final report
    }

    public static void removeTest() {
        test.remove(); // ✅ cleans up thread-local after scenario
    }
}
