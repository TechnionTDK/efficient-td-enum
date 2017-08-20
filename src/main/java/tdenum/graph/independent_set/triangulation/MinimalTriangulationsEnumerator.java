package tdenum.graph.independent_set.triangulation;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.SeparatorGraph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.graphs.interfaces.ISeparatorGraph;
import tdenum.graph.independent_set.IndSetExtBySeparators;
import tdenum.graph.independent_set.IndSetExtByTriangulation;
import tdenum.graph.independent_set.MaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.separators.SeparatorsScoringCriterion;
import tdenum.graph.independent_set.Converter;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public class MinimalTriangulationsEnumerator {

    IGraph graph;
    ISeparatorGraph seperatorGraph;
    MinimalTriangulator triangulator;
    IndSetExtByTriangulation triExtender;
    IndSetExtBySeparators sepExtender;
    IndSetScorerByTriangulation scorer;
    MaximalIndependentSetsEnumerator<MinimalSeparator> setsEnumerator;

    public MinimalTriangulationsEnumerator(final IGraph g, TriangulationScoringCriterion triC,
                                           SeparatorsScoringCriterion sepC, TriangulationAlgorithm heuristic)
    {
        graph = new Graph(g);
        seperatorGraph = new SeparatorGraph(graph, sepC);
        triangulator = new MinimalTriangulator(heuristic);
        triExtender = new IndSetExtByTriangulation(graph, triangulator);
        sepExtender = new IndSetExtBySeparators(graph);
        scorer = new IndSetScorerByTriangulation(graph, triC);
        setsEnumerator = new MaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, triExtender, scorer);
    }

    public boolean hasNext()
    {
        return setsEnumerator.hasNext();

    }

    public IChordalGraph next()
    {
        return Converter.minimalSeparatorsToTriangulation(graph, setsEnumerator.next());
    }


    public int getNumberOfMinimalSeperatorsGenerated()
    {
        return seperatorGraph.getNumberOfNodesGenerated();

    }



}
