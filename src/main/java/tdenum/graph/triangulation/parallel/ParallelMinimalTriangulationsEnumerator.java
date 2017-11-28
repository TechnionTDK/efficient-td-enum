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
import tdenum.graph.triangulation.AbstractMinimalTriangulationsEnumerator;
import tdenum.legacy.graph.independent_set.LegacyMaximalIndependentSetsEnumerator;
import tdenum.graph.separators.SeparatorsScoringCriterion;
import tdenum.graph.independent_set.scoring.single_thread.IndSetScorerByTriangulation;
import tdenum.graph.triangulation.minimal_triangulators.MinimalTriangulator;
import tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;
import tdenum.graph.triangulation.TriangulationScoringCriterion;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelMinimalTriangulationsEnumerator  extends AbstractMinimalTriangulationsEnumerator {


    IChordalGraph nextChordalGraph;


    Set<IChordalGraph> triangulations = ConcurrentHashMap.newKeySet();

    public Set<IChordalGraph> getTriangulations() {
        return triangulations;
    }

    @Override
    public boolean hasNext()

    {
        return setsEnumerator.hasNext();

    }

    @Override
    public IChordalGraph next()
    {
        setsEnumerator.next();
        return nextChordalGraph;
    }


    @Override
    public int getNumberOfMinimalSeperatorsGenerated()
    {
        return seperatorGraph.getNumberOfNodesGenerated();

    }

    @Override
    public void print(Set<MinimalSeparator> result) {
        IChordalGraph chordalGraph= Converter.minimalSeparatorsToTriangulation(graph, result);
        nextChordalGraph = chordalGraph;
        triangulations.add(chordalGraph);

        resultPrinter.print(chordalGraph);
    }
}
