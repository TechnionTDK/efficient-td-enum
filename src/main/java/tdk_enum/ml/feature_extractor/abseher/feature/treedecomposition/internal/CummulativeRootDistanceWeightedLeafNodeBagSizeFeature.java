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
public class CummulativeRootDistanceWeightedLeafNodeBagSizeFeature extends TreeDecompositionFeature {
    
    public CummulativeRootDistanceWeightedLeafNodeBagSizeFeature() {
        super("CummulativeRootDistanceWeightedLeafNodeBagSize");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IMLGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            double value = 0;
            
            if (!td.isEmpty()) {
                int maximumDepth = td.getMaximumDepth();
                
                for (DecompositionNode node : td.getLeafNodeList()) {
                    if (node != null) {
                        value += node.getItemCount() * (1 + maximumDepth - node.getDepth());
                    }
                }
            }
            
            ret = new StatisticsSummary(value);
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, true);
    }
    
}
