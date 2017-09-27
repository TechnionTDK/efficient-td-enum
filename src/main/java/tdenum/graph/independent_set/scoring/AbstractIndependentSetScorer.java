package tdenum.graph.independent_set.scoring;

import tdenum.graph.data_structures.Edge;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.triangulation.TriangulationScoringCriterion;

import java.util.HashSet;
import java.util.Set;



public abstract class AbstractIndependentSetScorer implements IIndependentSetScorer<MinimalSeparator> {




    protected IGraph graph;
    protected TriangulationScoringCriterion criterion;
    protected Set<Edge> seenFillEdges = new HashSet<>();


    @Override
    public void setGraph(IGraph graph) {

        this.graph = graph;
    }

    @Override
    public void setCriterion(TriangulationScoringCriterion criterion) {
        this.criterion = criterion;
    }
}
