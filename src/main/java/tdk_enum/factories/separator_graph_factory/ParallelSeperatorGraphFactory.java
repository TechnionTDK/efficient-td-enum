package tdk_enum.factories.separator_graph_factory;

import tdk_enum.common.Utils;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.common.configuration.config_types.SeparatorsGraphType;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.minimal_separators_enumerator_factory.MinimalSeperatorsEnumeratorFactory;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.ISeparatorGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.ConcurrentSeparatorsGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.DemonSeparatorGraph;

import static tdk_enum.common.configuration.config_types.SeparatorsGraphType.PARALLEL;

public class ParallelSeperatorGraphFactory implements ISeparatorGraphFactory {
    @Override
    public ISeparatorGraph produce() {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        SeparatorsGraphType graphType = (SeparatorsGraphType) Utils.getFieldValue(configuration, "separatorsGraphType", PARALLEL );
        System.out.println("Producing Parallel Separates Graph. Type :" + graphType);
        switch (graphType)
        {
            case PARALLEL:
                return inject(new ConcurrentSeparatorsGraph());
            case DEMON:
                return inject(new DemonSeparatorGraph());
            default:
                return inject(new ConcurrentSeparatorsGraph());

        }
    }

    ISeparatorGraph inject(ISeparatorGraph separatorGraph) {
        separatorGraph.setGraph(TDKEnumFactory.getGraph());
        separatorGraph.setNodesEnumerator(new MinimalSeperatorsEnumeratorFactory().produce());
        return separatorGraph;
    }

}
