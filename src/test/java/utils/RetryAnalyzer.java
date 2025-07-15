package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int maxRetryCount = 1; // Retry only once

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            System.out.println("🔁 RETRYING: " + result.getName() + " | Retry Count: " + (retryCount + 1));
            retryCount++;
            ExtentManager.getTest().info("🔁 Retrying scenario due to failure...");
            return true;
        }
        return false;
    }
}
