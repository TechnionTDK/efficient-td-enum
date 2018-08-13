package tdk_enum;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tdk_enum.graph.graphs.ChordalGraphTest;
import tdk_enum.graph.graphs.GraphTest;
import tdk_enum.graph.graphs.SeparatorGraphTest;
import tdk_enum.enumerators.independent_set.IndSetExtByTriangulationTest;
import tdk_enum.enumerators.separators.MinimalSeparatorsEnumeratorTest;
import tdk_enum.enumerators.separators.SeparatorScorerTest;
import tdk_enum.enumerators.triangulation.IndSetScorerByTriangulationTest;
import tdk_enum.enumerators.triangulation.LegacyMinimalTriangulationsEnumeratorTest;
import tdk_enum.enumerators.triangulation.MinimalTriangulationEnumeratorTest;
import tdk_enum.enumerators.triangulation.MinimalTriangulatorTest;
import tdk_enum.enumerators.independent_set.ConverterTest;

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
