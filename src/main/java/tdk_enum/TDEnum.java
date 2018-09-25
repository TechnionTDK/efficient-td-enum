package tdk_enum;

import tdk_enum.common.IO.InputFile;
import tdk_enum.common.IO.logger.Logger;
import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.common.Utils;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.configuration_parser.ConfigurationParserFactory;
import tdk_enum.factories.enumerator_factory.EnumeratorFactory;

import javax.rmi.CORBA.Util;
import java.util.*;
import java.util.concurrent.*;



public class TDEnum {





    static InputFile inputFile;
//    static int separators;
    static long totalTimeInMilis;
    static long timeLimit = Long.MAX_VALUE;
    static IEnumerator enumerator;

    static String configFile = null;
    static List<TDKEnumConfiguration> experimentConfigurations = new ArrayList<>();
    static Map<Integer, Integer> benchMarkResults = new HashMap<>();

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

        StringBuilder sb = new StringBuilder();
        sb.
                append("Starting enumeration for ").append(inputFile.getField()).append("\\").
                append(inputFile.getType()).append("\\").
                append(inputFile.getName());
        System.out.println(sb.toString());
        run();

        //TDKEnumFactory.getCacheManager().close();
    }



    static void init(String filePath)
    {
        inputFile = new InputFile(filePath);

        if (configFile != null)
        {
            experimentConfigurations = new ConfigurationParserFactory().produce(configFile).parseConfigFile();

        }
        TDKEnumFactory.init(inputFile);
        Logger.init();
        Logger.setField(inputFile.getField());
        Logger.setType(inputFile.getType());
        Logger.setGraph(inputFile.getName());
        Logger.setFileName(inputFile.getPath());
    }

    static void run()
    {
        int id = 1;
        for(TDKEnumConfiguration experimentConfiguration : experimentConfigurations)
        {
            TDKEnumFactory.setConfiguration(experimentConfiguration);


            ExecutorService executorService = Executors.newFixedThreadPool(1);
            long startTime = System.nanoTime();

            if(experimentConfiguration.getTime_limit()==0 || experimentConfiguration.getEnumerationPurpose().equals(EnumerationPurpose.BENCHMARK_COMPARE))
            {
                TDKEnumFactory.setBenchMarkResults(benchMarkResults.get(experimentConfiguration.getBenchMarkBaseId()));
                enumerator = new EnumeratorFactory().produce();
                startTime = System.nanoTime();
                runWithoutTimeLimit(executorService, Arrays.asList(enumerator));
            }
            else
            {
                enumerator = new EnumeratorFactory().produce();
                startTime = System.nanoTime();
                runTimeLimited(executorService,Arrays.asList(enumerator), experimentConfiguration.getTime_limit());
                timeLimit = experimentConfiguration.getTime_limit() *1000;
            }
            long finishTime = System.nanoTime() - startTime;


            executorService.shutdown();
            totalTimeInMilis = TimeUnit.NANOSECONDS.toMillis(finishTime);
            try {
                executorService.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ForkJoinPool.commonPool().shutdownNow();
            executorService.shutdownNow();
            benchMarkResults.put(id, ((IResultHandler)enumerator.getResultPrinter()).getResultsFound());
            printResults();
            id++;

        }



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

        IResultHandler resultHandler = (IResultHandler) enumerator.getResultPrinter();
        if (totalTimeInMilis >=  timeLimit)
        {
            System.out.println("Time limit reached");
            resultHandler.setTimeLimitExeeded(true);
        }
        else
        {
            System.out.println("All possible results were generated!");
        }
        resultHandler.setEndTime(totalTimeInMilis);

        if (Utils.doesObjectContainMethod(resultHandler,"setSeparators") && Utils.doesObjectContainMethod(enumerator, "getNumberOfMinimalSeperatorsGenerated"))
        {
            Utils.runSetter(resultHandler, "setSeparators", Utils.runGetter(enumerator, "getNumberOfMinimalSeperatorsGenerated",0));
        }


        resultHandler.printSummaryTable();


        StringBuilder sb = new StringBuilder();
        sb.
                append("The graph has ").append(TDKEnumFactory.getGraph().getNumberOfNodes()).append(" nodes and ").
                append(TDKEnumFactory.getGraph().getNumberOfEdges()).append(" edges. ");

        System.out.println(sb.toString());


        Logger.setResults(resultHandler.getResultsFound());
        Logger.setTime(totalTimeInMilis);


        Logger.printLogs();
    }


}
