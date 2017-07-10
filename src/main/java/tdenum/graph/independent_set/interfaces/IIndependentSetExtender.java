package tdenum.graph.independent_set.interfaces;

import java.util.Set;

/**
 * Created by dvird on 17/07/10.
 */
public interface IIndependentSetExtender<T>
{
    Set<T> extendToMaxIndependentSet(final Set<T> s);
}
