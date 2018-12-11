package tdk_enum.ml.feature_extractor.abseher.feature;

import tdk_enum.ml.solvers.execution.CommandResult;

import java.io.File;
import java.text.DecimalFormat;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class BenchmarkRun {
    private int tdID = 0;

    private File instance = null;

    private double userTime = -1;
    private double systemTime = -1;
    private double wallClockTime = -1;

    private boolean memoryError = false;
    private boolean timeoutError = false;
    private boolean exitCodeError = false;

    private double memoryConsumption = -1;

    private String command = "UNKNOWN";

    private long outputLineCount = -1;
    private long errorLineCount = -1;

    private int exitCode = -1;

    private BenchmarkRun(int tdID,
                         File instance,
                         double userTime,
                         double systemTime,
                         double wallClockTime,
                         double memoryConsumption,
                         boolean memoryError,
                         boolean timeoutError,
                         boolean exitCodeError,
                         long outputLineCount,
                         long errorLineCount,
                         String command,
                         int exitCode) {

        this.tdID = tdID;
        this.command = command;
        this.exitCode = exitCode;
        this.instance = instance;

        this.userTime = userTime;
        this.systemTime = systemTime;
        this.wallClockTime = wallClockTime;

        this.memoryError = memoryError;
        this.timeoutError = timeoutError;
        this.exitCodeError = exitCodeError;

        this.memoryConsumption = memoryConsumption;

        this.outputLineCount = outputLineCount;
        this.errorLineCount = errorLineCount;
    }

    public int getTdID() {
        return tdID;
    }

    public int getExitCode() {
        return exitCode;
    }

    public File getInstance() {
        return instance;
    }

    public double getUserTime() {
        return userTime;
    }

    public double getSystemTime() {
        return systemTime;
    }

    public double getWallClockTime() {
        return wallClockTime;
    }

    public double getMemoryConsumption() {
        return memoryConsumption;
    }

    public boolean isMemoryError() {
        return memoryError;
    }

    public boolean isTimeoutError() {
        return timeoutError;
    }

    public boolean isExitCodeError() {
        return exitCodeError;
    }

    public long getOutputLineCount() {
        return outputLineCount;
    }

    public long getErrorLineCount() {
        return errorLineCount;
    }

    public static BenchmarkRun getInstance(int tdID,
                                           File instance,
                                           double userTime,
                                           double systemTime,
                                           double wallClockTime,
                                           double memoryConsumption,
                                           boolean memoryError,
                                           boolean timeoutError,
                                           boolean exitCodeError,
                                           long outputLineCount,
                                           long errorLineCount,
                                           String command,
                                           int exitCode) {
        BenchmarkRun ret = null;

        if (instance != null) {
            ret = new BenchmarkRun(tdID,
                    instance,
                    userTime,
                    systemTime,
                    wallClockTime,
                    memoryConsumption,
                    memoryError,
                    timeoutError,
                    exitCodeError,
                    outputLineCount,
                    errorLineCount,
                    command,
                    exitCode);
        }

        return ret;
    }

    public static BenchmarkRun getInstance(int tdID,
                                           File instance) {
        BenchmarkRun ret = null;

        if (instance != null) {
            ret = new BenchmarkRun(tdID,
                    instance,
                    -1,
                    -1,
                    -1,
                    -1,
                    false,
                    false,
                    false,
                    -1,
                    -1,
                    null,
                    -1);
        }

        return ret;
    }

    public static BenchmarkRun getInstance(int tdID, File instance, CommandResult commandResult)
    {
        BenchmarkRun ret = null;
        if(instance != null)
        {
            ret = new BenchmarkRun(tdID, instance,
                    commandResult.getTotalDuration_UserTime(),
                    commandResult.getTotalDuration_SystemTime(),
                    commandResult.getTotalDuration_WallClockTime(),
                    commandResult.getPeakMemory(),
                    commandResult.isMemoryExceeded(),
                    commandResult.isTimeoutOccurred(),
                    commandResult.isExitCodeErrorOccurred(),
                    commandResult.getOutputLineCount(),
                    commandResult.getErrorLineCount(),
                    commandResult.getCommandString(),
                    commandResult.getExitCode());
        }
        return  ret;

    }

    public String toCSV() {
        DecimalFormat format =
                new DecimalFormat("0.000");

        StringBuilder sb =
                new StringBuilder();

        sb.append("\"");
        sb.append(instance);
        sb.append("\",");
        sb.append(tdID);
        sb.append(",");

        if (!Double.isNaN(userTime) && userTime >= 0) {
            sb.append(format.format(userTime));
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (!Double.isNaN(systemTime) && systemTime >= 0) {
            sb.append(format.format(systemTime));
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (!Double.isNaN(wallClockTime) && wallClockTime >= 0) {
            sb.append(format.format(wallClockTime));
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (!Double.isNaN(memoryConsumption) && memoryConsumption >= 0) {
            sb.append(format.format(memoryConsumption));
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (memoryError) {
            sb.append(1);
        }
        else {
            sb.append(0);
        }

        sb.append(",");

        if (timeoutError) {
            sb.append(1);
        }
        else {
            sb.append(0);
        }

        sb.append(",");

        if (exitCodeError) {
            sb.append(1);
        }
        else {
            sb.append(0);
        }

        sb.append(",");

        if (exitCode >= 0) {
            sb.append(exitCode);
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (outputLineCount >= 0) {
            sb.append(outputLineCount);
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (errorLineCount >= 0) {
            sb.append(errorLineCount);
        }
        else {
            sb.append("UNKNOWN");
        }

        sb.append(",");

        if (command != null) {
            sb.append(StringOperations.escapeForCSV(command));
        }
        else {
            sb.append("UNKNOWN");
        }

        return sb.toString();
    }

    public static String getCSVHeaders() {
        return "Instance,tdID,\"User-Time\",\"System-Time\",\"Wall-Clock-Time\",\"MemoryConsumption\",\"Number of Memory-Errors\",\"Number of Timeout-Errors\",\"Number of Exitcode-Errors\",\"Exit-Code\",\"Lines stdout\",\"Lines stderr\",Command";
    }

    public static BenchmarkRun importCSV(String csv) {
        BenchmarkRun ret = null;

        if (csv != null) {
            String[] values =
                    StringOperations.readFromCSV(csv);

            if (values.length >= 13) {
                String path =
                        values[0];

                if (path.startsWith("\"")) {
                    path = path.substring(1);
                }

                if (path.endsWith("\"")) {
                    path = path.substring(0, path.length() - 1);
                }

                File instance = new File(path);

                try {
                    int tdID =
                            Integer.parseInt(values[1]);

                    double userTime = Double.NaN;

                    if (values[2] != null && !values[2].equals("?") && !values[2].equals("UNKNOWN")) {
                        userTime = Double.parseDouble(values[2]);
                    }

                    double systemTime = Double.NaN;

                    if (values[3] != null && !values[3].equals("?") && !values[3].equals("UNKNOWN")) {
                        systemTime = Double.parseDouble(values[3]);
                    }

                    double wallClockTime = Double.NaN;

                    if (values[4] != null && !values[4].equals("?") && !values[4].equals("UNKNOWN")) {
                        wallClockTime = Double.parseDouble(values[4]);
                    }

                    double memoryConsumption = Double.NaN;

                    if (values[5] != null && !values[5].equals("?") && !values[5].equals("UNKNOWN")) {
                        memoryConsumption = Double.parseDouble(values[5]);
                    }

                    boolean memoryError =
                            Integer.parseInt(values[6]) != 0;

                    boolean timeoutError =
                            Integer.parseInt(values[7]) != 0;

                    boolean exitCodeError =
                            Integer.parseInt(values[8]) != 0;

                    int exitCode =
                            Integer.parseInt(values[9]);

                    int outputLineCount =
                            Integer.parseInt(values[10]);

                    int errorLineCount =
                            Integer.parseInt(values[11]);

                    ret = getInstance(tdID,
                            instance,
                            userTime,
                            systemTime,
                            wallClockTime,
                            memoryConsumption,
                            memoryError,
                            timeoutError,
                            exitCodeError,
                            outputLineCount,
                            errorLineCount,
                            values[12],
                            exitCode);
                }
                catch (NumberFormatException ex) {

                }
            }
        }

        return ret;
    }

}
