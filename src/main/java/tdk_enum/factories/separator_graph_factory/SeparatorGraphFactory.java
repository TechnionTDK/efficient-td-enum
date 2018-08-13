package tdk_enum.factories.separator_graph_factory;

import tdk_enum.common.Utils;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.common.configuration.config_types.SeparatorsGraphType;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.minimal_separators_enumerator_factory.MinimalSeperatorsEnumeratorFactory;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.ISeparatorGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.ConcurrentSeparatorsGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.DemonSeparatorGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.single_thread.SeparatorGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.single_thread.improvements.CachableSeparatorGraph;

import static tdk_enum.common.configuration.config_types.SeparatorsGraphType.CACHED;
import static tdk_enum.common.configuration.config_types.SeparatorsGraphType.PARALLEL;
import static tdk_enum.common.configuration.config_types.SeparatorsGraphType.VANILLA;

public class SeparatorGraphFactory implements ISeparatorGraphFactory {


    @Override
    public ISeparatorGraph produce() {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        switch (configuration.getRunningMode())
        {
            case SINGLE_THREAD:
                return new SingleThreadSeparatorGraphFactory().produce();
            case PARALLEL:
                return new ParallelSeperatorGraphFactory().produce();
            default:
                return new SingleThreadSeparatorGraphFactory().produce();
        }
    }
}
