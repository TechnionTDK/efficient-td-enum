package tdk_enum.factories;

import org.ehcache.CacheManager;
import org.ehcache.Status;
import org.ehcache.config.builders.CacheManagerBuilder;
import tdk_enum.RunningMode;
import tdk_enum.common.IO.GraphReader;
import tdk_enum.common.IO.InputFile;
import tdk_enum.factories.cache_factory.ICacheFactory;
import tdk_enum.factories.cache_factory.ParallelCacheFactory;
import tdk_enum.factories.cache_factory.SingleThreadCacheFactory;
import tdk_enum.factories.enumeration_runner_factory.IEnumerationRunnerFactory;
import tdk_enum.factories.enumeration_runner_factory.ParallelEnumerationRunnerFactory;
import tdk_enum.factories.enumeration_runner_factory.SingleThreadEnumerationRunnerFactory;
import tdk_enum.factories.idconverter_factory.IIDConverterFactory;
import tdk_enum.factories.maximal_independent_sets_enumerator_factory.IMaximalIndependentSetsEnumeratorFactory;
import tdk_enum.factories.maximal_independent_sets_enumerator_factory.ParallelMaximalIndependentSetsEnumeratorFactory;
import tdk_enum.factories.maximal_independent_sets_enumerator_factory.SingleThreadMaximalIndependentSetsEnumeratorFactory;
import tdk_enum.factories.minimal_separators_enumerator_factory.IMinimalSeparatorsEnumeratorFactory;
import tdk_enum.factories.minimal_separators_enumerator_factory.SingleThreadMinimalSeparatorsEnumeratorFactory;
import tdk_enum.factories.minimal_triangulations_enumerator_factory.IMinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.minimal_triangulations_enumerator_factory.ParallelMinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.minimal_triangulations_enumerator_factory.SingleThreadMinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.minimal_triangulator_factory.IMinimalTriangulatorFactory;
import tdk_enum.factories.minimal_triangulator_factory.MinimalTriangulatorFactory;
import tdk_enum.factories.result_handler_factory.IResultHandlerFactory;
import tdk_enum.factories.result_handler_factory.ParallelResultHandlerFactory;
import tdk_enum.factories.result_handler_factory.SingleThreadResultHandlerFactory;
import tdk_enum.factories.separator_graph_factory.ISeparatorGraphFactory;
import tdk_enum.factories.separator_graph_factory.ParallelSeparatorGraphFactory;
import tdk_enum.factories.separator_graph_factory.SingleThreadSeparatorGraphFactory;
import tdk_enum.factories.separator_scorer_factory.ISeparatorScorerFactory;
import tdk_enum.factories.separator_scorer_factory.SeparatorScorerFactory;
import tdk_enum.factories.set_scorer_factory.ISetScorerFactory;
import tdk_enum.factories.set_scorer_factory.SetScorerFactory;
import tdk_enum.factories.sets_extender_factory.ISetsExtenderFactory;
import tdk_enum.factories.sets_extender_factory.SetsExtenderFactory;
import tdk_enum.factories.weighted_queue_factory.IWeightedQueueFactory;
import tdk_enum.factories.weighted_queue_factory.ParallelWeightedQueueFactory;
import tdk_enum.factories.weighted_queue_factory.SingleThreadWeightedQueueFactory;
import tdk_enum.graph.graphs.IGraph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static tdk_enum.RunningMode.PARALLEL;

public class TDEnumFactory {



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

    static Properties properties;

    static InputFile inputFile;



    static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();



    static int singleThreadResults;



    public static void init(IGraph graph)
    {
        System.out.println("number of available cores " + Runtime.getRuntime().availableProcessors());
        if(TDEnumFactory.inputFile==null)
        {
            TDEnumFactory.inputFile = new InputFile("");
        }

        TDEnumFactory.graph = graph;



        //default
        minimalTriangulatorFactory = new MinimalTriangulatorFactory();
        setScorerFactory = new SetScorerFactory();
        setsExtenderFactory = new SetsExtenderFactory();
        separatorScorerFactory = new SeparatorScorerFactory();

        minimalSeparatorsEnumeratorFactory = new SingleThreadMinimalSeparatorsEnumeratorFactory();
        if (cacheManager.getStatus().equals(Status.UNINITIALIZED))
        {
            cacheManager.init();
        }

        //single thread
        if (!RunningMode.valueOf(getProperties().getProperty("mode")).equals(PARALLEL))
        {
            minimalTriangulationsEnumeratorFactory = new SingleThreadMinimalTriangulationsEnumeratorFactory();
            maximalIndependentSetsEnumeratorFactory = new SingleThreadMaximalIndependentSetsEnumeratorFactory();



            weightedQueueFactory = new SingleThreadWeightedQueueFactory();

            separatorGraphFactory = new SingleThreadSeparatorGraphFactory();
            resultHandlerFactory = new SingleThreadResultHandlerFactory();

            cacheFactory = new SingleThreadCacheFactory();

            enumerationRunnerFactory = new SingleThreadEnumerationRunnerFactory();



        }

        else
        {
            maximalIndependentSetsEnumeratorFactory = new ParallelMaximalIndependentSetsEnumeratorFactory();
            minimalTriangulationsEnumeratorFactory = new ParallelMinimalTriangulationsEnumeratorFactory();
//            minimalSeparatorsEnumeratorFactory = new ParallelMinimalSeparatorsEnumeratorFactory();
//            separatorGraphFactory = new ParallelSeparatorGraphFactory();

            weightedQueueFactory = new ParallelWeightedQueueFactory();

            resultHandlerFactory = new ParallelResultHandlerFactory();

            separatorGraphFactory = new ParallelSeparatorGraphFactory();
            cacheFactory = new ParallelCacheFactory();

            enumerationRunnerFactory = new ParallelEnumerationRunnerFactory();



        }

    }


    public static void moveToParallel()
    {
        minimalTriangulatorFactory = new MinimalTriangulatorFactory();
        setScorerFactory = new SetScorerFactory();
        setsExtenderFactory = new SetsExtenderFactory();
        separatorScorerFactory = new SeparatorScorerFactory();

        minimalSeparatorsEnumeratorFactory = new SingleThreadMinimalSeparatorsEnumeratorFactory();
        maximalIndependentSetsEnumeratorFactory = new ParallelMaximalIndependentSetsEnumeratorFactory();
        minimalTriangulationsEnumeratorFactory = new ParallelMinimalTriangulationsEnumeratorFactory();
//            minimalSeparatorsEnumeratorFactory = new ParallelMinimalSeparatorsEnumeratorFactory();
//            separatorGraphFactory = new ParallelSeparatorGraphFactory();

        weightedQueueFactory = new ParallelWeightedQueueFactory();

        resultHandlerFactory = new ParallelResultHandlerFactory();

        separatorGraphFactory = new ParallelSeparatorGraphFactory();
        cacheFactory = new ParallelCacheFactory();

        enumerationRunnerFactory = new ParallelEnumerationRunnerFactory();
    }

    public static void moveToSingleThread()
    {

    }

    public static void init(InputFile inputFile)
    {
        TDEnumFactory.inputFile = inputFile;
        IGraph graph = GraphReader.read(inputFile.getPath());
        init(graph);

    }

    public static IMinimalTriangulationsEnumeratorFactory getMinimalTriangulationsEnumeratorFactory() {
        return minimalTriangulationsEnumeratorFactory;
    }

    public static void setMinimalTriangulationsEnumeratorFactory(IMinimalTriangulationsEnumeratorFactory minimalTriangulationsEnumeratorFactory) {
        TDEnumFactory.minimalTriangulationsEnumeratorFactory = minimalTriangulationsEnumeratorFactory;
    }

    public static IMaximalIndependentSetsEnumeratorFactory getMaximalIndependentSetsEnumeratorFactory() {
        return maximalIndependentSetsEnumeratorFactory;
    }

    public static void setMaximalIndependentSetsEnumeratorFactory(IMaximalIndependentSetsEnumeratorFactory maximalIndependentSetsEnumeratorFactory) {
        TDEnumFactory.maximalIndependentSetsEnumeratorFactory = maximalIndependentSetsEnumeratorFactory;
    }

    public static IMinimalSeparatorsEnumeratorFactory getMinimalSeparatorsEnumeratorFactory() {
        return minimalSeparatorsEnumeratorFactory;
    }

    public static void setMinimalSeparatorsEnumeratorFactory(IMinimalSeparatorsEnumeratorFactory minimalSeparatorsEnumeratorFactory) {
        TDEnumFactory.minimalSeparatorsEnumeratorFactory = minimalSeparatorsEnumeratorFactory;
    }

    public static ISeparatorGraphFactory getSeparatorGraphFactory() {
        return separatorGraphFactory;
    }

    public static void setSeparatorGraphFactory(ISeparatorGraphFactory separatorGraphFactory) {
        TDEnumFactory.separatorGraphFactory = separatorGraphFactory;
    }

    public static ISetsExtenderFactory getSetsExtenderFactory() {
        return setsExtenderFactory;
    }

    public static void setSetsExtenderFactory(ISetsExtenderFactory setsExtenderFactory) {
        TDEnumFactory.setsExtenderFactory = setsExtenderFactory;
    }

    public static ISetScorerFactory getSetScorerFactory() {
        return setScorerFactory;
    }

    public static void setSetScorerFactory(ISetScorerFactory setScorerFactory) {
        TDEnumFactory.setScorerFactory = setScorerFactory;
    }

    public static IMinimalTriangulatorFactory getMinimalTriangulatorFactory() {
        return minimalTriangulatorFactory;
    }

    public static void setMinimalTriangulatorFactory(IMinimalTriangulatorFactory minimalTriangulatorFactory) {
        TDEnumFactory.minimalTriangulatorFactory = minimalTriangulatorFactory;
    }

    public static IWeightedQueueFactory getWeightedQueueFactory() {
        return weightedQueueFactory;
    }

    public static void setWeightedQueueFactory(IWeightedQueueFactory weightedQueueFactory) {
        TDEnumFactory.weightedQueueFactory = weightedQueueFactory;
    }

    public static IGraph getGraph() {
        return graph;
    }

    public static void setGraph(IGraph graph) {
        TDEnumFactory.graph = graph;
    }

    public static Properties getProperties() {

        if (properties == null)
        {
            properties = new Properties();
            try (InputStream input = new FileInputStream("config.properties"))
            {
                properties.load(input);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public static void setProperties(Properties inproperties) {
        properties = inproperties;
    }


    public static ISeparatorScorerFactory getSeparatorScorerFactory() {
        return separatorScorerFactory;
    }

    public static void setSeparatorScorerFactory(ISeparatorScorerFactory separatorScorerFactory) {
        TDEnumFactory.separatorScorerFactory = separatorScorerFactory;
    }

    public static IEnumerationRunnerFactory getEnumerationRunnerFactory() {
        return enumerationRunnerFactory;
    }

    public static void setEnumerationRunnerFactory(IEnumerationRunnerFactory enumerationRunnerFactory) {
        TDEnumFactory.enumerationRunnerFactory = enumerationRunnerFactory;
    }

    public static IResultHandlerFactory getResultHandlerFactory() {
        return resultHandlerFactory;
    }

    public static void setResultHandlerFactory(IResultHandlerFactory resultHandlerFactory) {
        TDEnumFactory.resultHandlerFactory = resultHandlerFactory;
    }


    public static IIDConverterFactory getConverterFactory() {
        return converterFactory;
    }

    public static void setConverterFactory(IIDConverterFactory converterFactory) {
        TDEnumFactory.converterFactory = converterFactory;
    }


    public static ICacheFactory getCacheFactory() {
        return cacheFactory;
    }

    public static void setCacheFactory(ICacheFactory cacheFactory) {
        TDEnumFactory.cacheFactory = cacheFactory;
    }

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
        TDEnumFactory.cacheManager = cacheManager;
    }

    public static int getSingleThreadResults() {
        return singleThreadResults;
    }

    public static void setSingleThreadResults(int singleThreadResults) {
        TDEnumFactory.singleThreadResults = singleThreadResults;
    }
}
