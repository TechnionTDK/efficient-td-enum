package tdenum.loggable.independent_set;

import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.independent_set.IndSetExtBySeparators;

public class LoggableIndSetExtBySeparators extends IndSetExtBySeparators {

    public LoggableIndSetExtBySeparators() {
    }

    public LoggableIndSetExtBySeparators(IGraph graph) {
        super(graph);
    }
}
