package tdk_enum.graph.independent_set.set_extender.single_thread.loggable;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.independent_set.set_extender.single_thread.IndSetExtBySeparators;

public class LoggableIndSetExtBySeparators extends IndSetExtBySeparators {

    public LoggableIndSetExtBySeparators() {
    }

    public LoggableIndSetExtBySeparators(IGraph graph) {
        super(graph);
    }
}
