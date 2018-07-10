package tdk_enum;

import tdk_enum.common.IO.InputFile;
import tdk_enum.common.IO.logger.Logger;
import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.common.runner.IEnumerationRunner;
import tdk_enum.factories.TDEnumFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

import static tdk_enum.RunningMode.MIXED;


public class TDEnum {





    static InputFile inputFile;
    static int separators;
    static long totalTimeInSeconds;
    static long timeLimit = Long.MAX_VALUE;
    static IEnumerationRunner runner;
    static String configFile = null;

    public static void main(String[] args)
    {
        if (args.length < 1) {
            System.out.println("No graph file specified");
            return;
        }
        if (args.length ==2)
        {
            configFile = args[1];
        }
        init(args[0]);

        run();
        printResults();
        runIfMixed();
        TDEnumFactory.getCacheManager().close();
    }

    private static void runIfMixed() {
        if (RunningMode.valueOf(TDEnumFactory.getProperties().getProperty("mode")).equals(MIXED))
        {
            TDEnumFactory.setSingleThreadResults(runner.getResultHandler().getResultsFound());
            TDEnumFactory.moveToParallel();
            run();
            printResults();
        }
    }


    static void init(String filePath)
    {
        inputFile = new InputFile(filePath);

        if (configFile != null)
        {
            Properties properties = new Properties();
            try (InputStream input = new FileInputStream(configFile))
            {
                properties.load(input);
                TDEnumFactory.setProperties(properties);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TDEnumFactory.init(inputFile);
        Logger.init();
        Logger.setField(inputFile.getField());
        Logger.setType(inputFile.getType());
        Logger.setGraph(inputFile.getName());
        Logger.setFileName(inputFile.getPath());
    }

    static void run()
    {
        StringBuilder sb = new StringBuilder();
        sb.
                append("Starting enumeration for ").append(inputFile.getField()).append("\\").
                append(inputFile.getType()).append("\\").
                append(inputFile.getName());
        System.out.println(sb.toString());

        runner = TDEnumFactory.getEnumerationRunnerFactory().produce();
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        long startTime = System.nanoTime();
        long finishTime = startTime;
        if (TDEnumFactory.getProperties().containsKey("time_limit") &&
                !Boolean.valueOf(TDEnumFactory.getProperties().getProperty("enumeratorSelfTimer")))
        {
            timeLimit = Integer.parseInt(TDEnumFactory.getProperties().getProperty("time_limit"));
            runTimeLimited(executorService,Arrays.asList(runner), timeLimit);
        }
        else
        {
            runWithoutTimeLimit(executorService, Arrays.asList(runner));

        }
        finishTime = System.nanoTime() - startTime;


        executorService.shutdown();
        totalTimeInSeconds = TimeUnit.NANOSECONDS.toSeconds(finishTime);
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ForkJoinPool.commonPool().shutdownNow();
        executorService.shutdownNow();





    }


    static void runTimeLimited(ExecutorService executorService, List<Callable<Object>> callables, long timeLimit)
    {
        List<Future<Object>> futures = new ArrayList<>();

        try {

            futures = executorService.invokeAll(callables, timeLimit, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for(Future f : futures)
        {
            if(!f.isDone())
            {
                f.cancel(true);
            }
        }



    }

    static void runWithoutTimeLimit(ExecutorService executorService, List<Callable<Object>> callables) {
        try {
            executorService.invokeAll(callables).get(0).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



    static void printResults()
    {
        separators = runner.getNumberOfMinimalSeparators();
        IResultHandler resultHandler = runner.getResultHandler();
        if (totalTimeInSeconds >=  timeLimit)
        {
            System.out.println("Time limit reached");
            resultHandler.setTimeLimitExeeded(true);
        }
        else
        {
            System.out.println("All minimal triangulations were generated!");
        }
        resultHandler.setEndTime(totalTimeInSeconds);
        resultHandler.setSeparators(separators);


        resultHandler.printSummaryTable();


        StringBuilder sb = new StringBuilder();
        sb.
                append("The graph has ").append(TDEnumFactory.getGraph().getNumberOfNodes()).append(" nodes and ").
                append(TDEnumFactory.getGraph().getNumberOfEdges()).append(" edges. ").
                append(separators).append(" minimal separators were generated in the process.");
        System.out.println(sb.toString());


        Logger.setResults(resultHandler.getResultsFound());
        Logger.setTime(totalTimeInSeconds);
        Logger.setSeparators(separators);

        Logger.printLogs();
    }


}
