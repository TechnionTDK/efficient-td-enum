package tdk_enum.ml.solvers;

import org.junit.Test;
import tdk_enum.common.configuration.config_types.MLProblemType;
import tdk_enum.ml.solvers.execution.CommandExecutor;
import tdk_enum.ml.solvers.execution.CommandResult;
import tdk_enum.ml.solvers.execution.FileSystemOperations;
import tdk_enum.ml.solvers.execution.MemoryFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DflatSolverTest {



    @Test
    public void test3()
    {
        ISolver dflatSolver = new DflatSolver();
        dflatSolver.setMemeoryLimit(67108864);
        dflatSolver.setTimeLimit(5000);
        File graphFile = new File("./dflat/instance_n20_p0.10_001.lp");
        File tdFile = new File("./dflat/instance_n20_p0.10_001_8.gml");
        CommandResult ret = dflatSolver.solve(graphFile, tdFile, MLProblemType.DFLAT_3COL);

    }


    @Test
    public void test2()
    {
        int memory = 67108864;
//        int memory = -1;

        int timeout = 5000;

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

        File targetFile = getFile("test");

        File targetFile_Error = getFile("test" + ".error");

        if (timeout > 0) {
            commandPrefix += " timeout -k 5 " +
                    ((long)Math.ceil((double)timeout / 1000) + 5);
        }
        String actualCommand = "./dflat/dflat -p ./dflat/problems/3col_dynamic.lp --graphml-in ./dflat/instance_n20_p0.10_001_8.gml -f ./dflat/instance_n20_p0.10_001.lp --depth 0 --output quiet";
        String command =
                commandPrefix + " " + actualCommand;

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

        System.out.println(exitCode);


    }


    protected File getFile(
                           String fileName) {

        File ret = null;



        ret = FileSystemOperations.getFile(fileName);


        return ret;
    }


    public void test1()
    {
        List<String> command = new ArrayList<>();

//        command.add("/bin/bash");
//        command.add("ulimit");
//        command.add("-d");
//        command.add("67108864");
//        command.add("-m");
//        command.add("67108864");
//        command.add("-v");
//        command.add("67108864;");





        command.add("\\time");
        command.add("-f");
        command.add("\"\\nUser-Time: %U\\nSystem-Time: %S\\nWall-Clock-Time: %e\\n\\nPeak-Memory: %M\"");


        command.add("timeout");
        command.add("-k");
        command.add("5");
        command.add("10");

        




        command.add("./dflat/dflat");
        command.add("-p");
        command.add("./dflat/problems/3col_dynamic.lp");
        command.add("--graphml-in");
        command.add("./dflat/instance_n20_p0.10_001_8.gml");
        command.add("-f");
        command.add("./dflat/instance_n20_p0.10_001.lp");
        command.add("--depth");
        command.add("0");
        command.add("--output");
        command.add("quiet");
        //"./dflat/dflat","-p", "./dflat/dynamic.lp",
        //                                                            "--graphml-in", "./dflat/1.gml",
        //                                                            "-f", "./dflat/instance_n20_p0.10_001.lp",
        //                                                            "--depth", "0"
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {

            String commandString = "ulimit -d 67108864 -m 67108864 -v 67108864;" +
                    " \\time --format=\"\\nUser-Time: %U\\nSystem-Time: %S\\nWall-Clock-Time: %e\\n\\nPeak-Memory: %M\"\" " +
                    " timeout -k 5 10 ./dflat/dflat -p ./dflat/problems/3col_dynamic.lp --graphml-in ./dflat/instance_n20_p0.10_001_8.gml -f ./dflat/instance_n20_p0.10_001.lp --deapth 0 --ouput quiet";
            String[] commandArray = {"/bin/sh", "-c", commandString};
            Process process = Runtime.getRuntime().exec(commandArray);
           // Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())) ;
            String line;
            while((line=reader.readLine())!=null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}