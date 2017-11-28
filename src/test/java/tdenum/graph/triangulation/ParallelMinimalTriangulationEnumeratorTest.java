package tdenum.graph.triangulation;

import org.junit.Before;
import org.junit.Test;
import tdenum.RunningMode;
import tdenum.common.IO.GraphReader;
import tdenum.factories.TDEnumFactory;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.triangulation.parallel.ParallelMinimalTriangulationsEnumerator;
import tdenum.legacy.graph.triangulation.LegacyMinimalTriangulationsEnumerator;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;
import static tdenum.RunningMode.PARALLEL;
import static tdenum.RunningMode.SINGLE;
import static tdenum.graph.separators.SeparatorsScoringCriterion.UNIFORM;
import static tdenum.graph.triangulation.TriangulationScoringCriterion.NONE;
import static tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.MCS_M;

public class ParallelMinimalTriangulationEnumeratorTest {

    @Before
    public void checkParallel()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\Easy\\BN\\CSP\\54.wcsp.uai");

        TDEnumFactory.init(g);
        RunningMode mode = RunningMode.valueOf(TDEnumFactory.getProperties().getProperty("mode"));
        assumeTrue(mode.equals(PARALLEL));
    }

    @Test
    public void compareToVanilla()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\Easy\\BN\\CSP\\54.wcsp.uai");

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
