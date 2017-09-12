package tdenum.parallel.independent_set.triangulation;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.SeparatorGraph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.graphs.interfaces.ISeparatorGraph;
import tdenum.graph.independent_set.Converter;
import tdenum.graph.independent_set.IndSetExtBySeparators;
import tdenum.graph.independent_set.IndSetExtByTriangulation;
import tdenum.graph.independent_set.MaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.separators.SeparatorsScoringCriterion;
import tdenum.graph.independent_set.triangulation.IndSetScorerByTriangulation;
import tdenum.graph.independent_set.triangulation.MinimalTriangulator;
import tdenum.graph.independent_set.triangulation.TriangulationAlgorithm;
import tdenum.graph.independent_set.triangulation.TriangulationScoringCriterion;

public class ParallelMinimalTriangulationsEnumerator {

    IGraph graph;
    ISeparatorGraph seperatorGraph;
    MinimalTriangulator triangulator;
    IndSetExtByTriangulation triExtender;
    IndSetExtBySeparators sepExtender;
    IndSetScorerByTriangulation scorer;
    MaximalIndependentSetsEnumerator<MinimalSeparator> setsEnumerator;

    boolean isTimeLimited;
    int timeLimit;
    public ParallelMinimalTriangulationsEnumerator(final IGraph g, TriangulationScoringCriterion triC,
                                           SeparatorsScoringCriterion sepC, TriangulationAlgorithm heuristic)
    {
        graph = new Graph(g);
        seperatorGraph = new SeparatorGraph(graph, sepC);
        triangulator = new MinimalTriangulator(heuristic);
        scorer = new IndSetScorerByTriangulation(graph, triC);

        int cores = Runtime.getRuntime().availableProcessors();


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
