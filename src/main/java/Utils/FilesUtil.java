package Utils;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class FilesUtil {
    FilesUtil() {
        super();
    }

    public static File getLatestFile(String folderPath)
    {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            Logs.warn("No files found in the directory: " + folderPath);
            return null;
        }
        File latestFile = files[0];
        for (File file : files) {
            if (file.isFile() && file.lastModified() > latestFile.lastModified()) {
                latestFile = file;
            }
        }
        Logs.info("Latest file in the directory: " + latestFile.getAbsolutePath());
        return latestFile;
    }

    /// Method to delete all files in a directory
    public static void deleteFiles(File dirPath) {
        if(dirPath == null || !dirPath.exists()) {
            Logs.warn("Directory does not exist: " + dirPath);
            return;
        }
        File[] filesList = dirPath.listFiles();
        if(filesList == null)
        {
            Logs.warn("Failed to List files in: " + dirPath);
            return;
        }
        for (File file : filesList) {
            if (file.isDirectory()) {
                deleteFiles(file);
            } else {
                if (file.delete()) {
                    Logs.info("Deleted files at: " + file.getAbsolutePath());
                } else {
                    Logs.warn("Failed to delete files at: " + file.getAbsolutePath());
                }
            }
        }
    }

    public static void cleanDirectory(File file)
    {
        try{
            FileUtils.cleanDirectory(file);
            Logs.info("Cleaned directory: " + file.getAbsolutePath());
        }
        catch (Exception e)
        {
            Logs.error("Failed to clean directory: " + file.getAbsolutePath() + " because: " + e.getMessage());
        }

    }

    public static void createDirectory(File dirPath) {
        if (!dirPath.exists()) {
            if (dirPath.mkdirs()) {
                Logs.info("Created directory: " + dirPath.getAbsolutePath());
            } else {
                Logs.warn("Failed to create directory: " + dirPath.getAbsolutePath());
            }
        } else {
            Logs.info("Directory already exists: " + dirPath.getAbsolutePath());
        }
    }
}