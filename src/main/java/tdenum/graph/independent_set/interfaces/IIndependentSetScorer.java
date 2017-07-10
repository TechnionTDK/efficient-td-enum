package tdenum.graph.independent_set.interfaces;

import java.util.Set;

/**
 * Created by dvird on 17/07/10.
 */
public interface IIndependentSetScorer<T>
{
    int scoreIndependentSet(final Set<T> s);
    boolean mayScoreChange();
    void independentSetUsed(final Set<T> s);
}
