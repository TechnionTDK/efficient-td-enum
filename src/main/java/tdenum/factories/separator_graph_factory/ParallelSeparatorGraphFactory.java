package tdenum.factories.separator_graph_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.graphs.succinct_graphs.separator_graph.ISeparatorGraph;
import tdenum.graph.graphs.succinct_graphs.separator_graph.parallel.ConcurrentSeparatorsGraph;

public class ParallelSeparatorGraphFactory implements ISeparatorGraphFactory {

    ISeparatorGraph separatorGraph;

    @Override
    public ISeparatorGraph produce() {
        if(separatorGraph == null)
        {
            separatorGraph = inject(new ConcurrentSeparatorsGraph());
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
