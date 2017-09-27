package tdenum;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tdenum.graph.graphs.ChordalGraphTest;
import tdenum.graph.graphs.GraphTest;
import tdenum.graph.graphs.SeparatorGraphTest;
import tdenum.graph.independent_set.IndSetExtByTriangulationTest;
import tdenum.graph.separators.MinimalSeparatorsEnumeratorTest;
import tdenum.graph.separators.SeparatorScorerTest;
import tdenum.graph.triangulation.IndSetScorerByTriangulationTest;
import tdenum.graph.triangulation.LegacyMinimalTriangulationsEnumeratorTest;
import tdenum.graph.triangulation.MinimalTriangulationEnumeratorTest;
import tdenum.graph.triangulation.MinimalTriangulatorTest;
import tdenum.graph.independent_set.ConverterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
                    GraphTest.class,
                    MinimalSeparatorsEnumeratorTest.class,
                    SeparatorGraphTest.class,
                    ConverterTest.class,
                    MinimalTriangulatorTest.class,
                    IndSetExtByTriangulationTest.class,
                    LegacyMinimalTriangulationsEnumeratorTest.class,
                    MinimalTriangulationEnumeratorTest.class,
                    IndSetScorerByTriangulationTest.class,
                    SeparatorScorerTest.class,
                    ChordalGraphTest.class})
public class TdEnumTestSuit {
}
