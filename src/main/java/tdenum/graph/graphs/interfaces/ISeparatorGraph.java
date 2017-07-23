package tdenum.graph.graphs.interfaces;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.NodeSet;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public interface ISeparatorGraph extends ISuccinctGraphRepresentation<MinimalSeparator > {

    int getNumberOfNodesGenerated();
}
