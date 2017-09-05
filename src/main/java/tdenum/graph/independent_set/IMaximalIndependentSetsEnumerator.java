package tdenum.graph.independent_set;

import java.util.Set;

public interface IMaximalIndependentSetsEnumerator<T> {

    boolean hasNext();
    Set<T> next();
}
