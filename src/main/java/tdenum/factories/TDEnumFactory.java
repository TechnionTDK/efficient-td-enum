package tdenum.factories;

import tdenum.RunningMode;
import tdenum.common.IO.GraphReader;
import tdenum.common.IO.InputFile;
import tdenum.factories.cache_factory.ICacheFactory;
import tdenum.factories.cache_factory.ParallelCacheFactory;
import tdenum.factories.cache_factory.SingleThreadCacheFactory;
import tdenum.factories.enumeration_runner_factory.IEnumerationRunnerFactory;
import tdenum.factories.enumeration_runner_factory.ParallelEnumerationRunnerFactory;
import tdenum.factories.enumeration_runner_factory.SingleThreadEnumerationRunnerFactory;
import tdenum.factories.idconverter_factory.IIDConverterFactory;
import tdenum.factories.maximal_independent_sets_enumerator_factory.IMaximalIndependentSetsEnumeratorFactory;
import tdenum.factories.maximal_independent_sets_enumerator_factory.ParallelMaximalIndependentSetsEnumeratorFactory;
import tdenum.factories.maximal_independent_sets_enumerator_factory.SingleThreadMaximalIndependentSetsEnumeratorFactory;
import tdenum.factories.minimal_separators_enumerator_factory.IMinimalSeparatorsEnumeratorFactory;
import tdenum.factories.minimal_separators_enumerator_factory.ParallelMinimalSeparatorsEnumeratorFactory;
import tdenum.factories.minimal_separators_enumerator_factory.SingleThreadMinimalSeparatorsEnumeratorFactory;
import tdenum.factories.minimal_triangulations_enumerator_factory.IMinimalTriangulationsEnumeratorFactory;
import tdenum.factories.minimal_triangulations_enumerator_factory.ParallelMinimalTriangulationsEnumeratorFactory;
import tdenum.factories.minimal_triangulations_enumerator_factory.SingleThreadMinimalTriangulationsEnumeratorFactory;
import tdenum.factories.minimal_triangulator_factory.IMinimalTriangulatorFactory;
import tdenum.factories.minimal_triangulator_factory.MinimalTriangulatorFactory;
import tdenum.factories.result_handler_factory.IResultHandlerFactory;
import tdenum.factories.result_handler_factory.ParallelResultHandlerFactory;
import tdenum.factories.result_handler_factory.SingleThreadResultHandlerFactory;
import tdenum.factories.separator_graph_factory.ISeparatorGraphFactory;
import tdenum.factories.separator_graph_factory.ParallelSeparatorGraphFactory;
import tdenum.factories.separator_graph_factory.SingleThreadSeparatorGraphFactory;
import tdenum.factories.separator_scorer_factory.ISeparatorScorerFactory;
import tdenum.factories.separator_scorer_factory.SeparatorScorerFactory;
import tdenum.factories.set_scorer_factory.ISetScorerFactory;
import tdenum.factories.set_scorer_factory.SetScorerFactory;
import tdenum.factories.sets_extender_factory.ISetsExtenderFactory;
import tdenum.factories.sets_extender_factory.SetsExtenderFactory;
import tdenum.factories.weighted_queue_factory.IWeightedQueueFactory;
import tdenum.factories.weighted_queue_factory.ParallelWeightedQueueFactory;
import tdenum.factories.weighted_queue_factory.SingleThreadWeightedQueueFactory;
import tdenum.graph.graphs.IGraph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static tdenum.RunningMode.PARALLEL;

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

    static Properties properties = new Properties();

    static InputFile inputFile;



    public static void init(IGraph graph)
    {
        if(TDEnumFactory.inputFile==null)
        {
            TDEnumFactory.inputFile = new InputFile("");
        }

        TDEnumFactory.graph = graph;


        try (InputStream input = new FileInputStream("config.properties"))
        {
            properties.load(input);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //default
        minimalTriangulatorFactory = new MinimalTriangulatorFactory();
        setScorerFactory = new SetScorerFactory();
        setsExtenderFactory = new SetsExtenderFactory();
        separatorScorerFactory = new SeparatorScorerFactory();
        separatorGraphFactory = new SingleThreadSeparatorGraphFactory();
        minimalSeparatorsEnumeratorFactory = new SingleThreadMinimalSeparatorsEnumeratorFactory();

        //single thread
        if (!RunningMode.valueOf(properties.getProperty("mode")).equals(PARALLEL))
        {
            minimalTriangulationsEnumeratorFactory = new SingleThreadMinimalTriangulationsEnumeratorFactory();
            maximalIndependentSetsEnumeratorFactory = new SingleThreadMaximalIndependentSetsEnumeratorFactory();



            weightedQueueFactory = new SingleThreadWeightedQueueFactory();


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
        return properties;
    }

    public static void setProperties(Properties properties) {
        TDEnumFactory.properties = properties;
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
}
