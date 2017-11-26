package tdenum.graph.triangulation.parallel;

import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.succinct_graphs.separator_graph.single_thread.SeparatorGraph;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.succinct_graphs.separator_graph.ISeparatorGraph;
import tdenum.graph.independent_set.Converter;
import tdenum.graph.independent_set.set_extender.single_thread.IndSetExtBySeparators;
import tdenum.graph.independent_set.set_extender.single_thread.IndSetExtByTriangulation;
import tdenum.legacy.graph.independent_set.LegacyMaximalIndependentSetsEnumerator;
import tdenum.graph.separators.SeparatorsScoringCriterion;
import tdenum.graph.independent_set.scoring.single_thread.IndSetScorerByTriangulation;
import tdenum.graph.triangulation.minimal_triangulators.MinimalTriangulator;
import tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;
import tdenum.graph.triangulation.TriangulationScoringCriterion;

public class ParallelMinimalTriangulationsEnumerator {

    IGraph graph;
    ISeparatorGraph seperatorGraph;
    MinimalTriangulator triangulator;
    IndSetExtByTriangulation triExtender;
    IndSetExtBySeparators sepExtender;
    IndSetScorerByTriangulation scorer;
    LegacyMaximalIndependentSetsEnumerator<MinimalSeparator> setsEnumerator;

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
