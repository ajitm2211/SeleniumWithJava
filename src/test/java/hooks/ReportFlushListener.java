package hooks;

import org.testng.IExecutionListener;
import utils.ExtentManager;

import java.awt.Desktop;
import java.io.File;

public class ReportFlushListener implements IExecutionListener {
    @Override
    public void onExecutionFinish() {
        ExtentManager.flush();
        try {
            String path = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            Desktop.getDesktop().browse(new File(path).toURI());
        } catch (Exception e) {
            System.err.println("❌ Could not auto-open Extent report: " + e.getMessage());
        }
    }
}
