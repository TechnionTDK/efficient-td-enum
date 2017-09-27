package tdenum.factories.separator_graph_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.graphs.separator_graph.ISeparatorGraph;
import tdenum.graph.graphs.separator_graph.parallel.ConcurrentSeparatorsGraph;

public class ParallelSeparatorGraphFactory implements ISeparatorGraphFactory {
    @Override
    public ISeparatorGraph produce() {
        return inject(new ConcurrentSeparatorsGraph());
    }

    ISeparatorGraph inject(ISeparatorGraph separatorGraph)
    {
        separatorGraph.setGraph(TDEnumFactory.getGraph());
        separatorGraph.setNodesEnumerator(TDEnumFactory.getMinimalSeparatorsEnumeratorFactory().produce());
        return separatorGraph;
    }

}
