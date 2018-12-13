package tdk_enum.factories;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.common.IO.GraphReader;
import tdk_enum.common.IO.InputFile;
import tdk_enum.factories.enumeration.cache_factory.ICacheFactory;
import tdk_enum.factories.enumeration.enumeration_runner_factory.IEnumerationRunnerFactory;
import tdk_enum.factories.enumeration.idconverter_factory.IIDConverterFactory;
import tdk_enum.factories.enumeration.maximal_independent_sets_enumerator_factory.IMaximalIndependentSetsEnumeratorFactory;
import tdk_enum.factories.enumeration.minimal_separators_enumerator_factory.IMinimalSeparatorsEnumeratorFactory;
import tdk_enum.factories.enumeration.minimal_triangulations_enumerator_factory.IMinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.enumeration.minimal_triangulator_factory.IMinimalTriangulatorFactory;
import tdk_enum.factories.enumeration.result_handler_factory.IResultHandlerFactory;
import tdk_enum.factories.enumeration.separator_graph_factory.ISeparatorGraphFactory;
import tdk_enum.factories.enumeration.separator_scorer_factory.ISeparatorScorerFactory;
import tdk_enum.factories.enumeration.set_scorer_factory.ISetScorerFactory;
import tdk_enum.factories.enumeration.sets_extender_factory.ISetsExtenderFactory;
import tdk_enum.factories.enumeration.weighted_queue_factory.IWeightedQueueFactory;
import tdk_enum.graph.graphs.IGraph;

public class TDKEnumFactory {



    static IMinimalTriangulationsEnumeratorFactory minimalTriangulationsEnumeratorFactory;
    static IMaximalIndependentSetsEnumeratorFactory maximalIndependentSetsEnumeratorFactory;
    static IMinimalSeparatorsEnumeratorFactory minimalSeparatorsEnumeratorFactory;
    static ISeparatorGraphFactory separatorGraphFactory;
    static ISetsExtenderFactory setsExtenderFactory;
    static ISetScorerFactory setScorerFactory;
    static IMinimalTriangulatorFactory minimalTriangulatorFactory;
    static IWeightedQueueFactory weightedQueueFactory;
    static ISeparatorScorerFactory separatorScorerFactory;
    static IEnumerationRunnerFactory enumerationRunnerFactory;

    static IResultHandlerFactory resultHandlerFactory;



    static ICacheFactory cacheFactory;



    static IIDConverterFactory converterFactory;


    static IGraph graph;

   // static Properties properties;

    public static void setInputFile(InputFile inputFile) {
        TDKEnumFactory.inputFile = inputFile;
    }

    static InputFile inputFile;


    static TDKEnumConfiguration configuration;

    public static TDKEnumConfiguration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(TDKEnumConfiguration configuration) {
        TDKEnumFactory.configuration = configuration;
    }

    static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();



    static int benchMarkResults;

    public static String getGraphName() {
        return inputFile.getName();
    }



    public static String getGraphPath() {
        return inputFile.getPath();
    }



    public static String getGraphType() {
        return inputFile.getType();
    }



    public static String getGraphField() {
        return inputFile.getField();
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }

    public static void setCacheManager(CacheManager cacheManager) {
        TDKEnumFactory.cacheManager = cacheManager;
    }

    public static int getBenchMarkResults() {
        return benchMarkResults;
    }

    public static void setBenchMarkResults(int benchMarkResults) {
        TDKEnumFactory.benchMarkResults = benchMarkResults;
    }


    public static IGraph getGraph() {
        return graph;
    }

    public static void setGraph(IGraph graph) {
        TDKEnumFactory.graph = graph;
    }

    public static InputFile getInputFile()
    {
        return inputFile;
    }

    public static void init(InputFile inputFile)
    {
        TDKEnumFactory.inputFile = inputFile;
        IGraph graph = GraphReader.read(inputFile.getPath());
        graph.setOriginalPath(inputFile.getPath());
        init(graph);

    }
    public static void init(IGraph graph)
    {
        System.out.println("number of available cores " + Runtime.getRuntime().availableProcessors());
        if(TDKEnumFactory.inputFile==null)
        {
            TDKEnumFactory.inputFile = new InputFile("");
        }

        TDKEnumFactory.graph = graph;





//        //default
//        minimalTriangulatorFactory = new MinimalTriangulatorFactory();
//        setScorerFactory = new SetScorerFactory();
//        setsExtenderFactory = new SetsExtenderFactory();
//        separatorScorerFactory = new SeparatorScorerFactory();
//
//        minimalSeparatorsEnumeratorFactory = new SingleThreadMinimalSeparatorsEnumeratorFactory();
//        if (cacheManager.getStatus().equals(Status.UNINITIALIZED))
//        {
//            cacheManager.init();
//        }
//
//        //single thread
//        if (!RunningMode.valueOf(getProperties().getProperty("mode")).equals(PARALLEL))
//        {
//            minimalTriangulationsEnumeratorFactory = new SingleThreadMinimalTriangulationsEnumeratorFactory();
//            maximalIndependentSetsEnumeratorFactory = new SingleThreadMaximalIndependentSetsEnumeratorFactory();
//
//
//
//            weightedQueueFactory = new SingleThreadWeightedQueueFactory();
//
//            separatorGraphFactory = new SingleThreadSeparatorGraphFactory();
//            resultHandlerFactory = new SingleThreadMinimalTriangulationsResultHandlerFactory();
//
//            cacheFactory = new SingleThreadCacheFactory();
//
//            enumerationRunnerFactory = new SingleThreadEnumerationRunnerFactory();
//
//
//
//        }
//
//        else
//        {
//            maximalIndependentSetsEnumeratorFactory = new ParallelMaximalIndependentSetsEnumeratorFactory();
//            minimalTriangulationsEnumeratorFactory = new ParallelMinimalTriangulationsEnumeratorFactory();
////            minimalSeparatorsEnumeratorFactory = new ParallelMinimalSeparatorsEnumeratorFactory();
////            separatorGraphFactory = new ParallelSeparatorGraphFactory();
//
//            weightedQueueFactory = new ParallelWeightedQueueFactory();
//
//            resultHandlerFactory = new ParallelMinimalTriangulationsResultHandlerFactory();
//
//            separatorGraphFactory = new ParallelSeparatorGraphFactory();
//            cacheFactory = new ParallelCacheFactory();
//
//            enumerationRunnerFactory = new ParallelEnumerationRunnerFactory();
//
//
//
//        }
//
//    }


//    public static void moveToParallel()
//    {
//        minimalTriangulatorFactory = new MinimalTriangulatorFactory();
//        setScorerFactory = new SetScorerFactory();
//        setsExtenderFactory = new SetsExtenderFactory();
//        separatorScorerFactory = new SeparatorScorerFactory();
//
//        minimalSeparatorsEnumeratorFactory = new SingleThreadMinimalSeparatorsEnumeratorFactory();
//        maximalIndependentSetsEnumeratorFactory = new ParallelMaximalIndependentSetsEnumeratorFactory();
//        minimalTriangulationsEnumeratorFactory = new ParallelMinimalTriangulationsEnumeratorFactory();
////            minimalSeparatorsEnumeratorFactory = new ParallelMinimalSeparatorsEnumeratorFactory();
////            separatorGraphFactory = new ParallelSeparatorGraphFactory();
//
//        weightedQueueFactory = new ParallelWeightedQueueFactory();
//
//        resultHandlerFactory = new ParallelMinimalTriangulationsResultHandlerFactory();
//
//        separatorGraphFactory = new ParallelSeparatorGraphFactory();
//        cacheFactory = new ParallelCacheFactory();
//
//        enumerationRunnerFactory = new ParallelEnumerationRunnerFactory();
 }

//    public static void moveToSingleThread()
//    {
//
//    }



//    public static IMinimalTriangulationsEnumeratorFactory getMinimalTriangulationsEnumeratorFactory() {
//        return minimalTriangulationsEnumeratorFactory;
//    }
//
//    public static void setMinimalTriangulationsEnumeratorFactory(IMinimalTriangulationsEnumeratorFactory minimalTriangulationsEnumeratorFactory) {
//        TDKEnumFactory.minimalTriangulationsEnumeratorFactory = minimalTriangulationsEnumeratorFactory;
//    }
//
//    public static IMaximalIndependentSetsEnumeratorFactory getMaximalIndependentSetsEnumeratorFactory() {
//        return maximalIndependentSetsEnumeratorFactory;
//    }
//
//    public static void setMaximalIndependentSetsEnumeratorFactory(IMaximalIndependentSetsEnumeratorFactory maximalIndependentSetsEnumeratorFactory) {
//        TDKEnumFactory.maximalIndependentSetsEnumeratorFactory = maximalIndependentSetsEnumeratorFactory;
//    }
//
//    public static IMinimalSeparatorsEnumeratorFactory getMinimalSeparatorsEnumeratorFactory() {
//        return minimalSeparatorsEnumeratorFactory;
//    }
//
//    public static void setMinimalSeparatorsEnumeratorFactory(IMinimalSeparatorsEnumeratorFactory minimalSeparatorsEnumeratorFactory) {
//        TDKEnumFactory.minimalSeparatorsEnumeratorFactory = minimalSeparatorsEnumeratorFactory;
//    }
//
//    public static ISeparatorGraphFactory getSeparatorGraphFactory() {
//        return separatorGraphFactory;
//    }
//
//    public static void setSeparatorGraphFactory(ISeparatorGraphFactory separatorGraphFactory) {
//        TDKEnumFactory.separatorGraphFactory = separatorGraphFactory;
//    }
//
//    public static ISetsExtenderFactory getSetsExtenderFactory() {
//        return setsExtenderFactory;
//    }

//    public static void setSetsExtenderFactory(ISetsExtenderFactory setsExtenderFactory) {
//        TDKEnumFactory.setsExtenderFactory = setsExtenderFactory;
//    }
//
//    public static ISetScorerFactory getSetScorerFactory() {
//        return setScorerFactory;
//    }
//
//    public static void setSetScorerFactory(ISetScorerFactory setScorerFactory) {
//        TDKEnumFactory.setScorerFactory = setScorerFactory;
//    }
//
//    public static IMinimalTriangulatorFactory getMinimalTriangulatorFactory() {
//        return minimalTriangulatorFactory;
//    }
//
//    public static void setMinimalTriangulatorFactory(IMinimalTriangulatorFactory minimalTriangulatorFactory) {
//        TDKEnumFactory.minimalTriangulatorFactory = minimalTriangulatorFactory;
//    }

//    public static IWeightedQueueFactory getWeightedQueueFactory() {
//        return weightedQueueFactory;
//    }
//
//    public static void setWeightedQueueFactory(IWeightedQueueFactory weightedQueueFactory) {
//        TDKEnumFactory.weightedQueueFactory = weightedQueueFactory;
//    }



//    public static Properties getProperties() {
//
//        if (properties == null)
//        {
//            properties = new Properties();
//            try (InputStream input = new FileInputStream("config.properties"))
//            {
//                properties.load(input);
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return properties;
//    }
//
//    public static void setProperties(Properties inproperties) {
//        properties = inproperties;
//    }


//    public static ISeparatorScorerFactory getSeparatorScorerFactory() {
//        return separatorScorerFactory;
//    }
//
//    public static void setSeparatorScorerFactory(ISeparatorScorerFactory separatorScorerFactory) {
//        TDKEnumFactory.separatorScorerFactory = separatorScorerFactory;
//    }
//
//    public static IEnumerationRunnerFactory getEnumerationRunnerFactory() {
//        return enumerationRunnerFactory;
//    }
//
//    public static void setEnumerationRunnerFactory(IEnumerationRunnerFactory enumerationRunnerFactory) {
//        TDKEnumFactory.enumerationRunnerFactory = enumerationRunnerFactory;
//    }
//
//    public static IResultHandlerFactory getResultHandlerFactory() {
//        return resultHandlerFactory;
//    }
//
//    public static void setResultHandlerFactory(IResultHandlerFactory resultHandlerFactory) {
//        TDKEnumFactory.resultHandlerFactory = resultHandlerFactory;
//    }


//    public static IIDConverterFactory getConverterFactory() {
//        return converterFactory;
//    }
//
//    public static void setConverterFactory(IIDConverterFactory converterFactory) {
//        TDKEnumFactory.converterFactory = converterFactory;
//    }


//    public static ICacheFactory getCacheFactory() {
//        return cacheFactory;
//    }
//
//    public static void setCacheFactory(ICacheFactory cacheFactory) {
//        TDKEnumFactory.cacheFactory = cacheFactory;
//    }


}
