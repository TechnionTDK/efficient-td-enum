package tdk_enum.factories.separator_graph_factory;

import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.ISeparatorGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.single_thread.SeparatorGraph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.single_thread.improvements.CachableSeparatorGraph;

public class SingleThreadSeparatorGraphFactory implements ISeparatorGraphFactory {

    ISeparatorGraph graph = null;

    @Override
    public ISeparatorGraph produce() {

        if (graph == null)
        {

            switch (SeparatorsGraphType.valueOf(TDEnumFactory.getProperties().getProperty("sepGraphType")))
            {
                case VANILLA:
                {
                    System.out.println("producing baseline separators graph");
                    return inject(new SeparatorGraph());
                }

                case CACHED:
                {
                    System.out.println("producing cachable separators graph");
                    return inject(new CachableSeparatorGraph());
                }
                default:
                {
                    System.out.println("producing cachable separators graph");
                    return inject(new CachableSeparatorGraph());
                }




            }

        }
        return  graph;

    }

    ISeparatorGraph inject(ISeparatorGraph separatorGraph)
    {
        separatorGraph.setGraph(TDEnumFactory.getGraph());
        separatorGraph.setNodesEnumerator(TDEnumFactory.getMinimalSeparatorsEnumeratorFactory().produce());
        graph = separatorGraph;
        return graph;
    }
}
