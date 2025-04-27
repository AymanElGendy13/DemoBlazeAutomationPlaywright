package Utils;

public class TerminalUtils {


    public static void executeCommand(String... command) {
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();
            Logs.info("Command executed: " + String.join(" ", command));
        }
        catch(Exception e)
        {
            Logs.info("Failed to execute command: " + String.join(" ", command) + " - " + e.getMessage());
        }
    }
}
