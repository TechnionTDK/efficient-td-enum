package tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.internal;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.TreeDecomposition;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;
import tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.TreeDecompositionFeature;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class CummulativeDepthWeightedJoinNodeBagSizeFeature extends TreeDecompositionFeature {
    
    public CummulativeDepthWeightedJoinNodeBagSizeFeature() {
        super("CummulativeDepthWeightedJoinNodeBagSize");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            double value = 0;
            
            if (!td.isEmpty()) {
                for (DecompositionNode node : td.getJoinNodeList()) {
                    if (node != null) {
                        value += node.getItemCount() * node.getDepth();
                    }
                }
            }
            
            ret = new StatisticsSummary(value);
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, true);
    }
    
}
