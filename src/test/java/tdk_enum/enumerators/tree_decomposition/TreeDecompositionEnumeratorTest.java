package tdk_enum.enumerators.tree_decomposition;

import org.junit.Test;
import tdk_enum.common.IO.GraphReader;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.configuration_parser.ConfigurationParserFactory;
import tdk_enum.factories.tree_decomposition_enumerator_factory.NiceTreeDecompositionEnumeratorFactory;
import tdk_enum.factories.tree_decomposition_enumerator_factory.TreeDecompositionEnumeratorFactory;
import tdk_enum.graph.converters.Converter;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.TreeDecompositionValidator;
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
            ITreeDecompositionEnumerator enumerator = new NiceTreeDecompositionEnumeratorFactory().produce();
            Set<ITreeDecomposition> newResultSet = new HashSet<>();
            while (enumerator.hasNext())
            {
                ITreeDecomposition result = enumerator.next();
               
                if (!newResultSet.add(result))
                {
                    System.out.println("TD already in");
                }
            }

            LegacyMinimalTriangulationsEnumerator legacyEnumerator = new LegacyMinimalTriangulationsEnumerator(TDKEnumFactory.getGraph(), NONE, UNIFORM, MCS_M);
            Set<ITreeDecomposition> legacyResults = new HashSet<>();
            while (legacyEnumerator.hasNext())
            {
                IChordalGraph result = legacyEnumerator.next();
                if(!legacyResults.add(Converter.chordalGraphToNiceTreeDecomposition( result)))
                {
                    System.out.println("TD already in legacy results");
                    System.out.println(result);
                }
            }

            assertEquals(legacyResults.size(), newResultSet.size());
            System.out.println("Size is" + legacyResults.size());
            assertEquals(legacyResults,newResultSet);
            for (ITreeDecomposition treeDecomposition : newResultSet)
            {
                assertTrue( TreeDecompositionValidator.isNiceTreeDecomposition(treeDecomposition,g));

            }
        }

    }
}
