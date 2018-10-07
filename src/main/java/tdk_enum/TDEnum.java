package tdk_enum;

import tdk_enum.common.IO.InputFile;
import tdk_enum.common.IO.logger.Logger;
import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.common.Utils;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.common.configuration.TDKMachineLearningConfiguration;
import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.configuration_parser.ConfigurationParserFactory;
import tdk_enum.factories.enumerator_factory.EnumeratorFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static tdk_enum.common.configuration.config_types.EnumerationPurpose.ML_CREATE_TEST_SET;
import static tdk_enum.common.configuration.config_types.EnumerationPurpose.ML_PREDICT;
import static tdk_enum.common.configuration.config_types.EnumerationPurpose.ML_TRAIN_MODEL;


public class TDEnum {









    static List<TDKEnumConfiguration> experimentConfigurations = new ArrayList<>();
    static List<TDKEnumConfiguration> mlConfigurations = new ArrayList<>();


    public static void main(String[] args)
    {
        if (args.length < 1) {
            System.out.println("Please provide a valid configuration file in json format and graph files or graphs folder");
            System.out.println("command line is : java -jar {jar_name}.jar {path_to_json_config_file} {path_to_input_file_or_folder}");
            System.out.println("see readme.md for more information");
            return;
        }

        if (!readConfigurations(args))
        {
            return;
        }



        run(args);

        //TDKEnumFactory.getCacheManager().close();
    }

    private static boolean readConfigurations(String[] args) {

        try {

        List<TDKEnumConfiguration> configurations = new ConfigurationParserFactory().produce(args[0]).parseConfigFile();
        mlConfigurations = configurations.stream().filter(tdkEnumConfiguration ->
                tdkEnumConfiguration.getEnumerationPurpose().equals(ML_CREATE_TEST_SET)||
                        tdkEnumConfiguration.getEnumerationPurpose().equals(ML_PREDICT)||
                        tdkEnumConfiguration.getEnumerationPurpose().equals(ML_TRAIN_MODEL)).collect(Collectors.toCollection(ArrayList::new));
        experimentConfigurations = configurations.stream().filter(tdkEnumConfiguration ->
                (!tdkEnumConfiguration.getEnumerationPurpose().equals(ML_CREATE_TEST_SET)&&
                        !tdkEnumConfiguration.getEnumerationPurpose().equals(ML_PREDICT)&&
                        !tdkEnumConfiguration.getEnumerationPurpose().equals(ML_TRAIN_MODEL))).collect(Collectors.toCollection(ArrayList::new));
        if (experimentConfigurations.size() > 0 && args.length < 2)
        {
            System.out.println("Please provide valid graph files or graphs folder for enumeration experiments");
            System.out.println("command line is : java -jar {jar_name}.jar {path_to_json_config_file} {path_to_input_file_or_folder}");
            System.out.println("see readme.md for more information");
            return false;
        }

        if (args.length <2 && !mlConfigurations.stream().allMatch(tdkEnumConfiguration ->
                ((TDKMachineLearningConfiguration)tdkEnumConfiguration).getInputPath()!=""))
        {
            System.out.println("Please provide valid graph files or graphs folder for ML experiments either by command line or the configuration file");
            System.out.println("command line is : java -jar {jar_name}.jar {path_to_json_config_file} {path_to_input_file_or_folder}");
            System.out.println("see readme.md for more information");
            return false;
        }


        return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }


    static void run(String[] args)

    {


        if (args.length ==2)
        {
            File input = new File(args[1]);
            List<String> inputs = new ArrayList<>();
            if (input.isDirectory())
            {
                getAllInputFiles(input, inputs);
            }
            else
            {
                inputs.add(args[1]);
            }
            runEnumerationExperiments(inputs);
            runMLExperiments();
        }
        else
        {
            runMLExperiments();
        }




    }

    private static void getAllInputFiles(File inputFolder, List<String> inputs) {
        for (File file :inputFolder.listFiles())
        {
            if(file.isDirectory())
            {
                getAllInputFiles(file, inputs);
            }
            else
            {
                inputs.add(file.getAbsolutePath());
            }
        }
    }

    private static void runMLExperiments() {
    }


    static boolean mlConfigurationFound(List<TDKEnumConfiguration> configurations)
    {
        return  configurations.stream().anyMatch(tdkEnumConfiguration ->
                tdkEnumConfiguration.getEnumerationPurpose().equals(ML_CREATE_TEST_SET)||
                        tdkEnumConfiguration.getEnumerationPurpose().equals(ML_PREDICT)||
                        tdkEnumConfiguration.getEnumerationPurpose().equals(ML_TRAIN_MODEL));
    }

    static void init(String filePath)
    {

        InputFile inputFile = new InputFile(filePath);


        TDKEnumFactory.init(inputFile);
        Logger.init();
        Logger.setField(inputFile.getField());
        Logger.setType(inputFile.getType());
        Logger.setGraph(inputFile.getName());
        Logger.setFileName(inputFile.getPath());
        StringBuilder sb = new StringBuilder();
        sb.
                append("Starting enumeration for ").append(inputFile.getField()).append("\\").
                append(inputFile.getType()).append("\\").
                append(inputFile.getName());
        System.out.println(sb.toString());
    }



    private static void runEnumerationExperiments(List<String> inputs) {


        for (String input : inputs)
        {
            init (input);
            Map<Integer, Integer> benchMarkResults = new HashMap<>();
            int id = 1;
            for(TDKEnumConfiguration experimentConfiguration : experimentConfigurations)
            {
                TDKEnumFactory.setConfiguration(experimentConfiguration);


                ExecutorService executorService = Executors.newFixedThreadPool(1);
                long startTime = System.nanoTime();
                long timeLimit = Long.MAX_VALUE;
                IEnumerator enumerator;
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
                long totalTimeInMilis = TimeUnit.NANOSECONDS.toMillis(finishTime);
                try {
                    executorService.awaitTermination(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ForkJoinPool.commonPool().shutdownNow();
                executorService.shutdownNow();
                benchMarkResults.put(id, ((IResultHandler)enumerator.getResultPrinter()).getResultsFound());
                printResults(enumerator, totalTimeInMilis, timeLimit );
                id++;

            }
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



    static void printResults(IEnumerator enumerator, long totalTimeInMillis, long timeLimit)
    {

        IResultHandler resultHandler = (IResultHandler) enumerator.getResultPrinter();
        if (totalTimeInMillis >=  timeLimit)
        {
            System.out.println("Time limit reached");
            resultHandler.setTimeLimitExeeded(true);
        }
        else
        {
            System.out.println("All possible results were generated!");
        }
        resultHandler.setEndTime(totalTimeInMillis);

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
        Logger.setTime(totalTimeInMillis);


        Logger.printLogs();
    }


}
