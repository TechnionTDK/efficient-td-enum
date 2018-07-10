package tdk_enum.graph.triangulation;

import org.junit.Before;
import org.junit.Test;
import tdk_enum.RunningMode;
import tdk_enum.common.IO.GraphReader;
import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.triangulation.parallel.ParallelMinimalTriangulationsEnumerator;
import tdk_enum.legacy.graph.triangulation.LegacyMinimalTriangulationsEnumerator;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;
import static tdk_enum.RunningMode.PARALLEL;
import static tdk_enum.graph.separators.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.graph.triangulation.TriangulationScoringCriterion.NONE;
import static tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.MCS_M;

public class ParallelMinimalTriangulationEnumeratorTest {

    @Before
    public void checkParallel()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\pace2016\\100\\AhrensSzekeresGeneralizedQuadrangleGraph_3.gr");

        TDEnumFactory.init(g);
        RunningMode mode = RunningMode.valueOf(TDEnumFactory.getProperties().getProperty("mode"));
        assumeTrue(mode.equals(PARALLEL));
    }

    @Test
    public void compareToVanilla()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\pace2016\\100\\AhrensSzekeresGeneralizedQuadrangleGraph_3.gr");

        TDEnumFactory.init(g);
        IMinimalTriangulationsEnumerator enumerator = TDEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce();
        while(enumerator.hasNext())
        {
            enumerator.next();
        }
        Set<IChordalGraph> newResultSet = ((ParallelMinimalTriangulationsEnumerator) enumerator).getTriangulations();


        LegacyMinimalTriangulationsEnumerator legacyEnumerator = new LegacyMinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
        Set<IChordalGraph> legacyResults = new HashSet<>();
        while (legacyEnumerator.hasNext())
        {
            legacyResults.add(legacyEnumerator.next());
        }
        assertEquals(legacyResults.size(), newResultSet.size());
        assertEquals(legacyResults,newResultSet);
    }

}
