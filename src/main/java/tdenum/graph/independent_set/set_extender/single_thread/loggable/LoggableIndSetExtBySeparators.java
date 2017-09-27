package tdenum.graph.independent_set.set_extender.single_thread.loggable;

import tdenum.graph.graphs.IGraph;
import tdenum.graph.independent_set.set_extender.single_thread.IndSetExtBySeparators;

public class LoggableIndSetExtBySeparators extends IndSetExtBySeparators {

    public LoggableIndSetExtBySeparators() {
    }

    public LoggableIndSetExtBySeparators(IGraph graph) {
        super(graph);
    }
}
