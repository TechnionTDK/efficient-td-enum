package tdk_enum.ml.solvers.execution;

import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class CommandResult {

    private int exitCode = -1;

    private long peakMemory = -1;
    private long errorLineCount = -1;
    private long outputLineCount = -1;
    private long totalDuration_UserTime = -1;
    private long totalDuration_SystemTime = -1;
    private long totalDuration_WallClockTime = -1;

    private String commandString = null;

    private boolean memoryExceeded = false;

    private boolean timeoutOccurred = false;

    private boolean timeoutValueUsed = false;

    private boolean exitCodeErrorOccurred = false;

    private Exception storedException = null;

    private MemoryFile errorContent = null;
    private MemoryFile outputContent = null;

    private CommandResult(String commandString, int exitCode, boolean timeoutOccurred, long outputLineCount, long errorLineCount) {
        this.commandString = commandString;

        this.errorLineCount = errorLineCount;
        this.outputLineCount = outputLineCount;

        if (exitCode >= 0) {
            this.exitCode = exitCode;
        }
        else {
            this.exitCode = -1;
        }

        this.timeoutOccurred = timeoutOccurred;
    }

    public boolean isValid() {
        return storedException == null &&
                getTotalDuration_UserTime() >= 0 &&
                getTotalDuration_SystemTime() >= 0 &&
                getTotalDuration_WallClockTime() >= 0;
    }

    public int getExitCode() {
        return exitCode;
    }

    public String getCommandString() {
        return commandString;
    }

    public Exception getException() {
        return storedException;
    }

    public void setException(Exception exception) {
        this.storedException = exception;
    }

    public long getPeakMemory() {
        return peakMemory;
    }

    public long getTotalDuration_UserTime() {
        return totalDuration_UserTime;
    }

    public long getTotalDuration_SystemTime() {
        return totalDuration_SystemTime;
    }

    public long getTotalDuration_WallClockTime() {
        return totalDuration_WallClockTime;
    }

    public long getErrorLineCount() {
        return errorLineCount;
    }

    public long getOutputLineCount() {
        return outputLineCount;
    }

    public boolean isMemoryExceeded() {
        return memoryExceeded;
    }

    public boolean isTimeoutOccurred() {
        return timeoutOccurred;
    }

    public boolean isExitCodeErrorOccurred() {
        return exitCodeErrorOccurred;
    }

    public MemoryFile getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(MemoryFile errorContent) {
        this.errorContent = errorContent;

        importResourceDetails();
    }

    public MemoryFile getOutputContent() {
        return outputContent;
    }

    public void setOutputContent(MemoryFile outputContent) {
        this.outputContent = outputContent;
    }

    public boolean isTimeoutValueUsed() {
        return timeoutValueUsed;
    }

    public void updateTimeoutValue(long timeoutValue) {
        if (timeoutOccurred)
        {
            if (timeoutValue >= 0) {
                timeoutValueUsed = true;

                totalDuration_UserTime = timeoutValue;
                totalDuration_SystemTime = 0;
                totalDuration_WallClockTime = timeoutValue;
            }
            else {
                timeoutValueUsed = false;

                totalDuration_UserTime = -1;
                totalDuration_SystemTime = -1;
                totalDuration_WallClockTime = -1;
            }
        }
    }

    public void verifyMemoryConsumption(long memoryLimit) {
        memoryExceeded = memoryLimit >= 0 && peakMemory >= memoryLimit;
    }

    public void verifyExitCode(List<Integer> permittedExitCodes) {
        if (exitCode != -1) {
            if (permittedExitCodes != null) {
                if (permittedExitCodes.isEmpty()) {
                    exitCodeErrorOccurred = false;
                }
                else {
                    boolean found = false;

                    for (Integer code : permittedExitCodes) {
                        if (code != null) {
                            if (code == exitCode) {
                                found = true;
                            }
                        }
                    }

                    exitCodeErrorOccurred = !found;
                }
            }
            else {
                exitCodeErrorOccurred = false;
            }
        }
        else {
            exitCodeErrorOccurred = false;
        }
    }

    public void importResourceDetails() {
        peakMemory = -1;

        totalDuration_UserTime = -1;
        totalDuration_SystemTime = -1;
        totalDuration_WallClockTime = -1;

        if (errorContent != null) {
            for (int i = 0; i < errorContent.getLineCount(); i++) {
                String line = errorContent.getLine(i);

                if (line.startsWith("User-Time: ")) {
                    line = line.substring("User-Time: ".length()).trim();

                    double userTime = getDouble(line);

                    if (userTime >= 0) {
                        totalDuration_UserTime = (long)(userTime * 1000);
                    }
                    else {
                        totalDuration_UserTime = -1;
                    }
                }
                else if (line.startsWith("System-Time: ")) {
                    line = line.substring("System-Time: ".length()).trim();

                    double systemTime = getDouble(line);

                    if (systemTime >= 0) {
                        totalDuration_SystemTime = (long)(systemTime * 1000);
                    }
                    else {
                        totalDuration_SystemTime = -1;
                    }
                }
                else if (line.startsWith("Wall-Clock-Time: ")) {
                    line = line.substring("Wall-Clock-Time: ".length()).trim();

                    double wallClockTime = getDouble(line);

                    if (wallClockTime >= 0) {
                        totalDuration_WallClockTime = (long)(wallClockTime * 1000);
                    }
                    else {
                        totalDuration_WallClockTime = -1;
                    }
                }
                else if (line.startsWith("Peak-Memory: ")) {
                    line = line.substring("Peak-Memory: ".length()).trim();

                    double peakMemoryData = getDouble(line);

                    if (peakMemoryData >= 0) {
                        peakMemory = (long)(peakMemoryData);
                    }
                    else {
                        peakMemory = -1;
                    }
                }
            }
        }
        else {
            peakMemory = -1;

            totalDuration_UserTime = -1;
            totalDuration_SystemTime = -1;
            totalDuration_WallClockTime = -1;
        }
    }

    public static CommandResult getInstance(String commandString, int exitCode, boolean timeoutOccurred) {
        return getInstance(commandString, exitCode, timeoutOccurred, 0, 0);
    }

    public static CommandResult getInstance(String commandString, int exitCode, boolean timeoutOccurred, long outputLineCount, long errorLineCount) {
        CommandResult ret = null;

        if (commandString != null) {
            ret = new CommandResult(commandString, exitCode, timeoutOccurred, outputLineCount, errorLineCount);
        }

        return ret;
    }

    private static double getDouble(String value) {
        double ret = -1;

        if (value != null) {
            try {
                ret = Double.parseDouble(value);
            }
            catch (NumberFormatException ex) {

            }
        }

        return ret;
    }


    @Override
    public String toString() {
        return "CommandResult{" +
                "exitCode=" + exitCode +
                ", peakMemory=" + peakMemory +
                ", errorLineCount=" + errorLineCount +
                ", outputLineCount=" + outputLineCount +
                ", totalDuration_UserTime=" + totalDuration_UserTime +
                ", totalDuration_SystemTime=" + totalDuration_SystemTime +
                ", totalDuration_WallClockTime=" + totalDuration_WallClockTime +
                ", commandString='" + commandString + '\'' +
                ", memoryExceeded=" + memoryExceeded +
                ", timeoutOccurred=" + timeoutOccurred +
                ", timeoutValueUsed=" + timeoutValueUsed +
                ", exitCodeErrorOccurred=" + exitCodeErrorOccurred +
                ", storedException=" + storedException +
                ", errorContent=" + errorContent +
                ", outputContent=" + outputContent +
                '}';
    }
}
