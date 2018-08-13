package tdk_enum.enumerators.independent_set.scoring;

import tdk_enum.graph.data_structures.Edge;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.common.configuration.config_types.TriangulationScoringCriterion;

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
