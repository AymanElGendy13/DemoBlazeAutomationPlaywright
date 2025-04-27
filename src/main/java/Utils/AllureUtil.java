package Utils;

import io.qameta.allure.Allure;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class AllureUtil {

    private AllureUtil() {
        super();
    }


    public static void attachLogsToAllureReport()
    {
        try{
            File logFile = FilesUtil.getLatestFile(Logs.LOGS_PATH);
            if(!logFile.exists())
            {
                Logs.warn("Log file does not exist: " + Logs.LOGS_PATH);
                return;
            }
            Allure.addAttachment("Logs.log", Files.readString(Path.of(logFile.getPath())));
            Logs.info("Attached logs to Allure report: " + logFile.getPath());
        }
        catch (Exception e)
        {
            Logs.error("Failed to attach logs to Allure report: " + e.getMessage());
        }
    }

    public static void attachScreenshotsToAllureReport(String screenshotName, String screenshotPath)
    {
        try{
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenshotPath)));
            Logs.info("Attached screenshot to Allure report: " + screenshotPath);
        }
        catch (Exception e)
        {
            Logs.error("Failed to attach screenshot to Allure report: " + e.getMessage());
        }
    }

}