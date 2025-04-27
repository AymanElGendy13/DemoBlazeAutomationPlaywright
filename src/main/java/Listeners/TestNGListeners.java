package Listeners;

import DriverSetup.DriverManager;
import Utils.AllureUtil;
import Utils.FilesUtil;
import Utils.Logs;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.testng.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Paths;

import static Utils.PropertiesUtil.loadProperties;

public class TestNGListeners implements IExecutionListener, ITestListener, IInvokedMethodListener {

    private File allureResults = new File("test-outputs/allure-results");
    private File logs = new File("test-outputs/Logs");
    private File screenshotsDir = new File("test-outputs/Screenshots");

    @Override
    public void onExecutionStart() {
        Logs.info("Test Execution Started");
        loadProperties();
        FilesUtil.deleteFiles(allureResults);
        FilesUtil.cleanDirectory(logs);
        FilesUtil.cleanDirectory(screenshotsDir);
        FilesUtil.createDirectory(allureResults);
        FilesUtil.createDirectory(logs);
        FilesUtil.createDirectory(screenshotsDir);
    }

    @Override
    public void onExecutionFinish() {
        Logs.info("Test Execution Finished");
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            Logs.info("Test Method " + method.getTestMethod().getMethodName() + " Finished");
            AllureUtil.attachLogsToAllureReport();

            // Get Playwright Page instance (assuming DriverManager provides it)
            Page page = DriverManager.getPage();
            if (page == null) return;

            String screenshotName = testResult.getName() + "_" + testResult.getStatus();
            takeScreenshot(page, screenshotName);
        }
    }

    /**
     * Takes a screenshot and attaches it to Allure.
     */
    private void takeScreenshot(Page page, String screenshotName) {
        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(screenshotsDir.getPath(), screenshotName + ".png")) // Save locally
                    .setFullPage(true) // Capture full page
            );

            // Attach to Allure report
            Allure.addAttachment(
                    screenshotName,
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );
        } catch (Exception e) {
            Logs.error("Failed to take screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        Logs.info("Test Case " + result.getName() + " Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Logs.info("Test Case " + result.getName() + " Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Logs.info("Test Case " + result.getName() + " Failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Logs.info("Test Case " + result.getName() + " Skipped");
    }
}