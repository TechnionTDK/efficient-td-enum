package tdk_enum;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tdk_enum.graph.graphs.ChordalGraphTest;
import tdk_enum.graph.graphs.GraphTest;
import tdk_enum.graph.graphs.SeparatorGraphTest;
import tdk_enum.graph.independent_set.IndSetExtByTriangulationTest;
import tdk_enum.graph.separators.MinimalSeparatorsEnumeratorTest;
import tdk_enum.graph.separators.SeparatorScorerTest;
import tdk_enum.graph.triangulation.IndSetScorerByTriangulationTest;
import tdk_enum.graph.triangulation.LegacyMinimalTriangulationsEnumeratorTest;
import tdk_enum.graph.triangulation.MinimalTriangulationEnumeratorTest;
import tdk_enum.graph.triangulation.MinimalTriangulatorTest;
import tdk_enum.graph.independent_set.ConverterTest;

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
