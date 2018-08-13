package tdk_enum.enumerators.triangulation;

import org.junit.Before;
import org.junit.Test;
import tdk_enum.common.configuration.config_types.RunningMode;
import tdk_enum.common.IO.GraphReader;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.enumerators.triangulation.parallel.ParallelMinimalTriangulationsEnumerator;
import tdk_enum.legacy.graph.triangulation.LegacyMinimalTriangulationsEnumerator;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;
import static tdk_enum.common.configuration.config_types.RunningMode.PARALLEL;
import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.NONE;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;

public class ParallelMinimalTriangulationEnumeratorTest {

    @Before
    public void checkParallel()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\pace2016\\100\\AhrensSzekeresGeneralizedQuadrangleGraph_3.gr");

        TDKEnumFactory.init(g);
//        RunningMode mode = RunningMode.valueOf(TDKEnumFactory.getProperties().getProperty("mode"));
//        assumeTrue(mode.equals(PARALLEL));
    }

    @Test
    public void compareToVanilla()
    {
//        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\pace2016\\100\\AhrensSzekeresGeneralizedQuadrangleGraph_3.gr");
//
//        TDKEnumFactory.init(g);
//        IMinimalTriangulationsEnumerator enumerator = TDKEnumFactory.getMinimalTriangulationsEnumeratorFactory().produce();
////        while(enumerator.hasNext())
////        {
////            enumerator.next();
////        }
//        enumerator.executeAlgorithm();
//        Set<IChordalGraph> newResultSet = ((ParallelMinimalTriangulationsEnumerator) enumerator).getTriangulations();
//
//
//        LegacyMinimalTriangulationsEnumerator legacyEnumerator = new LegacyMinimalTriangulationsEnumerator(g, NONE, UNIFORM, MCS_M);
//        Set<IChordalGraph> legacyResults = new HashSet<>();
//        while (legacyEnumerator.hasNext())
//        {
//            legacyResults.add(legacyEnumerator.next());
//        }
//        assertEquals(legacyResults.size(), newResultSet.size());
//        assertEquals(legacyResults,newResultSet);
    }

}
