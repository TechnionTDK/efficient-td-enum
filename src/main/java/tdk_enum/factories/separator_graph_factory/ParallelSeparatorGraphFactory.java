package tdk_enum.factories.separator_graph_factory;

import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.ISeparatorGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.ConcurrentSeparatorsGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.parallel.DemonSeparatorGraph;

public class ParallelSeparatorGraphFactory implements ISeparatorGraphFactory {

    ISeparatorGraph separatorGraph;

    @Override
    public ISeparatorGraph produce() {
        if(separatorGraph == null)
        {
            SeparatorsGraphType separatorsGraphType = SeparatorsGraphType.valueOf(TDEnumFactory.getProperties().getProperty("sepGraphType"));
            switch (separatorsGraphType)
            {
                case DEMON:
                {
                    separatorGraph = inject(new DemonSeparatorGraph());
                    break;
                }
                default:
                {
                    separatorGraph = inject(new ConcurrentSeparatorsGraph());
                    break;
                }
            }

        }
        return separatorGraph;
    }

    ISeparatorGraph inject(ISeparatorGraph separatorGraph)
    {
        separatorGraph.setGraph(TDEnumFactory.getGraph());
        separatorGraph.setNodesEnumerator(TDEnumFactory.getMinimalSeparatorsEnumeratorFactory().produce());
        return separatorGraph;
    }

}
