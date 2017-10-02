package tdenum.factories.separator_graph_factory;

import tdenum.TDEnum;
import tdenum.factories.TDEnumFactory;
import tdenum.graph.graphs.separator_graph.ISeparatorGraph;
import tdenum.graph.graphs.separator_graph.single_thread.SeparatorGraph;
import tdenum.graph.graphs.separator_graph.single_thread.improvements.CachableSeparatorGraph;

import static tdenum.factories.separator_graph_factory.SeparatorsGraphType.VANILLA;

public class SingleThreadSeparatorGraphFactory implements ISeparatorGraphFactory {

    ISeparatorGraph graph = null;

    @Override
    public ISeparatorGraph produce() {

        if (graph == null)
        {
            if (SeparatorsGraphType.valueOf(TDEnumFactory.getProperties().getProperty("sepGraphType")).equals(VANILLA))
            {

                return inject(new SeparatorGraph());
            }
            System.out.println("producing cachable separators graph");
            return inject(new CachableSeparatorGraph());
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
