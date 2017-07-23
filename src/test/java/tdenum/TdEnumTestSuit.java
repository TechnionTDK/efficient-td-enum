package tdenum;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tdenum.graph.graphs.GraphTest;
import tdenum.graph.graphs.SeparatorGraphTest;
import tdenum.graph.independent_set.IndSetExtByTriangulationTest;
import tdenum.graph.independent_set.separators.MinimalSeparatorsEnumeratorTest;
import tdenum.graph.independent_set.separators.SeparatorScorerTest;
import tdenum.graph.independent_set.triangulation.IndSetScorerByTriangulationTest;
import tdenum.graph.independent_set.triangulation.MinimalTriangulationsEnumeratorTest;
import tdenum.graph.independent_set.triangulation.MinimalTriangulatorTest;
import tdenum.graph.utils.ConverterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
                    GraphTest.class,
                    MinimalSeparatorsEnumeratorTest.class,
                    SeparatorGraphTest.class,
                    ConverterTest.class,
                    MinimalTriangulatorTest.class,
                    IndSetExtByTriangulationTest.class,
                    MinimalTriangulationsEnumeratorTest.class,
                    IndSetScorerByTriangulationTest.class,
                    SeparatorScorerTest.class})
public class TdEnumTestSuit {
}
