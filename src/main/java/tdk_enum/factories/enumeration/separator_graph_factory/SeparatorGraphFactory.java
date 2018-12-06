package tdk_enum.factories.enumeration.separator_graph_factory;

import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.ISeparatorGraph;

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
