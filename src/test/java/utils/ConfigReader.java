package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties props = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getReportType() {
        return props.getProperty("reportType", "extent").toLowerCase();
    }
    public static String getScreenshotMode() {
        return props.getProperty("screenshot", "fail").toLowerCase();
    }
    
    
}
