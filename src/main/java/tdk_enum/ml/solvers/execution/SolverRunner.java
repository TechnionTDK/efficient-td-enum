package tdk_enum.ml.solvers.execution;

import java.io.File;
import java.util.List;

public class SolverRunner {

    public static  CommandResult RunSolverCommand(String solverCommand, long memory, long timeout, List<Integer> permittedExitCodes, File graph)
    {
        CommandResult ret = null;

        String timeCommand =
                "\\time --format=\"\\n" +
                        "User-Time: %U\\nSystem-Time: %S\\n" +
                        "Wall-Clock-Time: %e\\n\\nPeak-Memory: %M\"";

        String memoryCommand = "";

        if (memory > 0) {
            memoryCommand = "ulimit -d " + memory + " -m " + memory + " -v " + memory + "; ";
        }

        String commandPrefix =
                memoryCommand + timeCommand;

        if (timeout > 0) {
            commandPrefix += " timeout -k 5 " +
                    ((long)Math.ceil((double)timeout / 1000) + 5);
        }

        String command =
                commandPrefix + " " + solverCommand;

        File targetFile = getFile("test");

        File targetFile_Error = getFile("test" + ".error");

        CommandExecutor currentExecutor = new CommandExecutor();

        String targetPath = "<UNKNOWN>";

        if (targetFile != null) {
            targetPath = targetFile.getAbsolutePath();
        }

        String errorPath = "<UNKNOWN>";

        if (targetFile_Error != null) {
            errorPath = targetFile_Error.getAbsolutePath();
        }

        MemoryFile errorFile =
                MemoryFile.getInstance(targetFile_Error, errorPath);

        errorFile.appendLine("COMMAND: " + command);

        MemoryFile outputFile =
                MemoryFile.getInstance(targetFile, targetPath);

        int exitCode =
                currentExecutor.execute(command, outputFile, errorFile, timeout);

        ret = CommandResult.getInstance(command, exitCode, currentExecutor.isTimeoutOccurred(),
                currentExecutor.getOutputLineCount(), currentExecutor.getErrorLineCount());

        if (exitCode >= 0) {
            ret.verifyExitCode(permittedExitCodes);
        }
        else {
            ret.setException(new Exception("Solution file for instance '" + graph.getAbsolutePath() + "' could not be created!"));
        }

        ret.setErrorContent(errorFile);
        ret.setOutputContent(outputFile);

        if (memory > 0) {
            ret.verifyMemoryConsumption(memory);
        }

//        if (timeoutValue >= 0) {
//            ret.updateTimeoutValue(timeoutValue);
//        }
        return ret;
    }

    protected static File getFile(String fileName) {

        File ret = null;



        ret = FileSystemOperations.getFile(fileName);


        return ret;
    }
}
