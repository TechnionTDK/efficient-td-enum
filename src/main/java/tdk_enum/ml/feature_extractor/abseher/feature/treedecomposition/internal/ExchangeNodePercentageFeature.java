package tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.internal;


import tdk_enum.graph.graphs.IMLGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;
import tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.TreeDecompositionFeature;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class ExchangeNodePercentageFeature extends TreeDecompositionFeature {
    
    public ExchangeNodePercentageFeature() {
        super("ExchangeNodePercentage");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IMLGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            double value = Double.NaN;
            
            if (!td.isEmpty()) {
                value = 0.0;
                
                for (DecompositionNode node : td.getExchangeNodeList()) {
                    if (node != null) {
                            value++;
                    }
                }
                
                value /= td.getNodeCount();
            }
            
            ret = new StatisticsSummary(value);
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, true);
    }
    
}
