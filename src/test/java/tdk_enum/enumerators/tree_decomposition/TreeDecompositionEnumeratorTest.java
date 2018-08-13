package tdk_enum.enumerators.tree_decomposition;

import org.junit.Test;
import tdk_enum.common.IO.GraphReader;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.configuration_parser.ConfigurationParserFactory;
import tdk_enum.factories.enumerator_factory.EnumeratorFactory;
import tdk_enum.factories.minimal_triangulations_enumerator_factory.ParallelMinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.tree_decomposition_enumerator_factory.TreeDecompositionEnumeratorFactory;
import tdk_enum.graph.graphs.Converter;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.TreeDecompositionValidator;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.legacy.graph.triangulation.LegacyMinimalTriangulationsEnumerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;
import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.NONE;

public class TreeDecompositionEnumeratorTest {

    @Test
    public void testCompareVannilaToNew()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\pace2016\\100\\AhrensSzekeresGeneralizedQuadrangleGraph_3.gr");

        TDKEnumFactory.init(g);
        ArrayList<TDKEnumConfiguration> experimentConfigurations = new ConfigurationParserFactory().produce("config.json").parseConfigFile();
        for (TDKEnumConfiguration configuration : experimentConfigurations)
        {
            TDKEnumFactory.setConfiguration(configuration);
            ITreeDecompositionEnumerator enumerator = new TreeDecompositionEnumeratorFactory().produce();
            Set<ITreeDecomposition> newResultSet = new HashSet<>();
            while (enumerator.hasNext())
            {
                newResultSet.add(enumerator.next());
            }

            LegacyMinimalTriangulationsEnumerator legacyEnumerator = new LegacyMinimalTriangulationsEnumerator(TDKEnumFactory.getGraph(), NONE, UNIFORM, MCS_M);
            Set<ITreeDecomposition> legacyResults = new HashSet<>();
            while (legacyEnumerator.hasNext())
            {
                legacyResults.add(Converter.chordalGraphToProperTreeDecomposition( legacyEnumerator.next()));
            }
            assertEquals(legacyResults.size(), newResultSet.size());
            assertEquals(legacyResults,newResultSet);
            for (ITreeDecomposition treeDecomposition : newResultSet)
            {
                assertTrue( TreeDecompositionValidator.isValidDecomposition(treeDecomposition,g));

            }
        }


    }
}
