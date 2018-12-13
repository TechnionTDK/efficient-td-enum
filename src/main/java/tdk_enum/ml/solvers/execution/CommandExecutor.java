package tdk_enum.ml.solvers.execution;

import java.io.IOException;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class CommandExecutor {

    private int lastExitCode = -1;

    private long lastDuration = -1;

    private long errorLineCount = -1;

    private long outputLineCount = -1;

    private Process currentProcess = null;

    private boolean timeoutOccurred = false;

    public CommandExecutor() {

    }

    public void abortCurrentProcess() {
        if (currentProcess != null) {
            currentProcess.destroy();
        }

        currentProcess = null;
    }

    public int getLastExitCode() {
        return lastExitCode;
    }

    public long getLastDuration() {
        return lastDuration;
    }

    public long getErrorLineCount() {
        return errorLineCount;
    }

    public long getOutputLineCount() {
        return outputLineCount;
    }

    public boolean isTimeoutOccurred() {
        return timeoutOccurred;
    }

    public void runRscript(String script)
    {
        try {

            Runtime runtime =
                    Runtime.getRuntime();
            currentProcess = runtime.exec(script);
            Worker worker =
                    new Worker(currentProcess);
            worker.start();
            worker.join(0);
            worker.interrupt();
            abortCurrentProcess();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runScript(String script)
    {
        try {
            String[] cmd = { "/bin/bash", "-c", script };
            Runtime runtime =
                    Runtime.getRuntime();
            currentProcess = runtime.exec(cmd);
            Worker worker =
                    new Worker(currentProcess);
            worker.start();
            worker.join(0);
            worker.interrupt();
            abortCurrentProcess();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int execute(String command) {
        return execute(command, null, null, 0);
    }

    public int execute(String command, long timeout) {
        return execute(command, null, null, timeout);
    }

    public int execute(String command, MemoryFile outputFile) {
        return execute(command, outputFile, null, 0);
    }

    public int execute(String command, MemoryFile outputFile, long timeout) {
        return execute(command, outputFile, null, timeout);
    }

    public int execute(String command, MemoryFile outputFile, MemoryFile errorFile) {
        return execute(command, outputFile, errorFile, 0);
    }

    public int execute(String command, MemoryFile outputFile, MemoryFile errorFile, long timeout) {
        int ret = -1;

        lastDuration = -1;

        timeoutOccurred = false;

        if (currentProcess == null && command != null) {
            String[] cmd = { "/bin/bash", "-c", command };

            try {
                Runtime runtime =
                        Runtime.getRuntime();

                StreamHandlerThread errorStreamHandler;
                StreamHandlerThread outputStreamHandler;
                do {
                    currentProcess = runtime.exec(cmd);
                    errorStreamHandler =
                            new StreamHandlerThread(currentProcess.getErrorStream(), errorFile);

                    outputStreamHandler =
                            new StreamHandlerThread(currentProcess.getInputStream(), outputFile);

                    errorStreamHandler.start();
                    outputStreamHandler.start();


                    Worker worker =
                            new Worker(currentProcess);

                    long start =
                            BenchmarkOperations.getWallClockTime();



                    worker.start();

                    try {
                        if (timeout < 0) {
                            worker.join();
                        }
                        else {
                            worker.join(timeout);
                        }

                        ret = worker.getExitCode();
                    }
                    catch(InterruptedException ex) {
                        ex.printStackTrace();
                        ret = -1;
                    }


                    worker.interrupt();



                    lastDuration =
                            BenchmarkOperations.getWallClockTime() - start;

                    timeoutOccurred = timeout > 0 && lastDuration >= timeout;

                    errorStreamHandler.abort();
                    outputStreamHandler.abort();

                    errorStreamHandler.join(1000);
                    outputStreamHandler.join(1000);
                    abortCurrentProcess();
                    errorLineCount =
                            errorStreamHandler.getCurrentLineCount();

                    outputLineCount =
                            outputStreamHandler.getCurrentLineCount();
                }
                while(errorLineCount==0);



            }catch (Exception e)
            {
                e.printStackTrace();
                abortCurrentProcess();

                outputLineCount = -1;

                errorLineCount = -1;
                ret =-1;
            }
//            catch (IOException | InterruptedException ex) {
//                abortCurrentProcess();
//
//                outputLineCount = -1;
//
//                errorLineCount = -1;
//
//                ret = -1;
//            }
        }

        lastExitCode = ret;

        return ret;
    }

    private static class Worker extends Thread {
        private int exitCode = -1;

        private final Process process;

        private Worker(Process process) {
            this.process = process;
        }

        public int getExitCode() {
            return exitCode;
        }

        @Override
        public void run() {
            if (process != null) {
                try {
                    exitCode =
                            process.waitFor();
                }
                catch (InterruptedException ex) {
                    process.destroy();

                    exitCode = -1;
                }
            }
        }
    }
}
