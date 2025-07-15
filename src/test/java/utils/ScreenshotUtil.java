package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String takeScreenshot(String scenarioName) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String browser = System.getProperty("browser", "chrome");
        String fileName = browser + "_" + scenarioName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".png";
        String screenshotsDir = System.getProperty("user.dir") + "/test-output/screenshots/";

        File screenshotFile = new File(screenshotsDir + fileName);
        TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
        File srcFile = ts.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(srcFile, screenshotFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return screenshotFile.getAbsolutePath(); // Return full path
    }
}
