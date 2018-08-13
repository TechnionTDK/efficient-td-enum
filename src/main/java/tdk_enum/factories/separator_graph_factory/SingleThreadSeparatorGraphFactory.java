package tdk_enum.factories.separator_graph_factory;

import tdk_enum.common.Utils;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.common.configuration.config_types.SeparatorsGraphType;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.minimal_separators_enumerator_factory.MinimalSeperatorsEnumeratorFactory;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.ISeparatorGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.single_thread.SeparatorGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.single_thread.improvements.CachableSeparatorGraph;

import static tdk_enum.common.configuration.config_types.SeparatorsGraphType.CACHED;

public class SingleThreadSeparatorGraphFactory implements ISeparatorGraphFactory {
    @Override
    public ISeparatorGraph produce() {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        SeparatorsGraphType graphType = (SeparatorsGraphType) Utils.getFieldValue(configuration, "separatorsGraphType", CACHED );

        System.out.println("Producing Single Thread Separates Graph. Type :" + graphType);
        switch (graphType)
        {
            case VANILLA:
                return inject(new SeparatorGraph());
            case CACHED:
                return inject(new CachableSeparatorGraph());
            default:
                return inject(new CachableSeparatorGraph());

        }
    }

    ISeparatorGraph inject(ISeparatorGraph separatorGraph) {
        separatorGraph.setGraph(TDKEnumFactory.getGraph());
        separatorGraph.setNodesEnumerator(new MinimalSeperatorsEnumeratorFactory().produce());
        return separatorGraph;
    }
}
