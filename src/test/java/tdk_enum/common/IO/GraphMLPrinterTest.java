package tdk_enum.common.IO;

import org.junit.Test;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.enumerators.tree_decomposition.ITreeDecompositionEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.configuration_parser.ConfigurationParserFactory;
import tdk_enum.factories.enumeration.tree_decomposition_enumerator_factory.NiceTreeDecompositionEnumeratorFactory;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.TreeDecompositionValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GraphMLPrinterTest {

    @Test
    public void testString()
    {
        IGraph g = GraphReader.read("/home/dvirdu/tddatasets/instances/3col/instances_width2/instance_n20_p0.10_001.lp");

        TDKEnumFactory.init(g);
        ArrayList<TDKEnumConfiguration> experimentConfigurations = new ConfigurationParserFactory().produce("config.json").parseConfigFile();
        for (TDKEnumConfiguration configuration : experimentConfigurations)
        {
            TDKEnumFactory.setConfiguration(configuration);
            ITreeDecompositionEnumerator enumerator = new NiceTreeDecompositionEnumeratorFactory().produce();
            Set<ITreeDecomposition> newResultSet = new HashSet<>();
            int i=1;
            while (enumerator.hasNext())
            {
                ITreeDecomposition result = enumerator.next();

                System.out.println(TreeDecompositionValidator.isNiceTreeDecomposition(result, g));
                System.out.println(result);
                //System.out.println(GraphMLPrinter.treeDecompositionToGraphMLFormat(result));
                GraphMLPrinter.treeDecompositionToGraphMLFile(result, "/home/dvirdu/tree decomposition/instances/3col/instances_width2/instance_n20_p0.10_001.lp/"+i+".gml");
                i++;
            }
        }
    }


}