package tdenum.graph.independent_set.separators;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.data_structures.NodeSet;

public interface IMinimalSeparatorsEnumerator {

    MinimalSeparator next();
    boolean hasNext();
    <T extends NodeSet> void minimalSeparatorFound(final T s);

}
