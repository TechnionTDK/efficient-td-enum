package tdenum;

import tdenum.common.IO.GraphReader;
import tdenum.common.IO.InputFile;
import tdenum.common.IO.logger.Logger;
import tdenum.common.IO.result_handler.IResultHandler;
import tdenum.common.runner.IEnumerationRunner;
import tdenum.factories.TDEnumFactory;
import tdenum.graph.graphs.IGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static tdenum.common.IO.CSVOperations.dataToCSV;


public class TDEnum {





    static InputFile inputFile;
    static int separators;
    static long totalTimeInSeconds;
    static long timeLimit = Long.MAX_VALUE;
    static IEnumerationRunner runner;

    public static void main(String[] args)
    {
        if (args.length < 1) {
            System.out.println("No graph file specified");
            return;
        }
        init(args[0]);
        run();
        printResults();
    }


    static void init(String filePath)
    {
        inputFile = new InputFile(filePath);
        TDEnumFactory.init(inputFile);


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
        if (TDEnumFactory.getProperties().containsKey("time_limit"))
        {
            timeLimit = Integer.parseInt(TDEnumFactory.getProperties().getProperty("time_limit"));
            runTimeLimited(executorService,Arrays.asList(runner), timeLimit);
        }
        else
        {
            runWithoutTimeLimit(executorService, Arrays.asList(runner));
        }
        finishTime = System.nanoTime() - startTime;
        separators = runner.getNumberOfMinimalSeparators();
        totalTimeInSeconds = TimeUnit.NANOSECONDS.toSeconds(finishTime);
        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();


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
            executorService.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    static void printResults()
    {
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
