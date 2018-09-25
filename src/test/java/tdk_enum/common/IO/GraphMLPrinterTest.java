package tdk_enum.common.IO;

import org.junit.Test;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.enumerators.tree_decomposition.ITreeDecompositionEnumerator;
import tdk_enum.enumerators.tree_decomposition.single_thread.TreeDecompositionEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.configuration_parser.ConfigurationParserFactory;
import tdk_enum.factories.tree_decomposition_enumerator_factory.NiceTreeDecompositionEnumeratorFactory;
import tdk_enum.factories.tree_decomposition_enumerator_factory.TreeDecompositionEnumeratorFactory;
import tdk_enum.graph.converters.Converter;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.TreeDecompositionValidator;
import tdk_enum.legacy.graph.triangulation.LegacyMinimalTriangulationsEnumerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;
import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.NONE;

public class GraphMLPrinterTest {

    @Test
    public void testString()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\instances\\3col\\instances_width2\\instance_n20_p0.10_001.lp");

        TDKEnumFactory.init(g);
        ArrayList<TDKEnumConfiguration> experimentConfigurations = new ConfigurationParserFactory().produce("config.json").parseConfigFile();
        for (TDKEnumConfiguration configuration : experimentConfigurations)
        {
            TDKEnumFactory.setConfiguration(configuration);
            ITreeDecompositionEnumerator enumerator = new TreeDecompositionEnumeratorFactory().produce();
            Set<ITreeDecomposition> newResultSet = new HashSet<>();
            int i=1;
            while (enumerator.hasNext())
            {
                ITreeDecomposition result = enumerator.next();

                System.out.println(TreeDecompositionValidator.isValidDecomposition(result, g));
                System.out.println(result);
                //System.out.println(GraphMLPrinter.treeDecompositionToGraphMLFormat(result));
                GraphMLPrinter.treeDecompositionToGraphMLFile(result, "C:\\tddatasets\\tree decompositions\\3col\\instances_width2\\instance_n20_p0.10_001.lp\\"+i+".gml");
                i++;
            }
        }
    }


}