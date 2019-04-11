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
public class ComplexityMeasureFeature extends TreeDecompositionFeature {
    
    public ComplexityMeasureFeature() {
        super("ComplexityMeasure");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IMLGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            ret = new StatisticsSummary(getComplexityMeasure(td));
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, true);
    }
    
    private double getComplexityMeasure(ITreeDecomposition td) {
        double ret = Double.NaN;
        
        if (!td.isEmpty()) {
            ret = getComplexityMeasure(td.getRoot());
        }

        return ret;
    }
    
    private double getComplexityMeasure(DecompositionNode node) {
        double ret = Double.NaN;
        
        /* TODO
        if (node != null) {
            ret = 1.0;
            
            if (node.isLeafNode()) {
                ret = Math.pow(1.10, node.getItemCount());
            }
            else {
                if (node.isJoinNode()) {
                    for (Node child : node.accessChildrenList()) {
                        ret *= getComplexityMeasure(child);
                    }
                    
                    ret *= 0.25;
                }
            
                ret *= Math.pow(1.10, node.getIntroducedItemList().size());
                ret /= Math.pow(1.10, node.getForgottenItemList().size());
                
                if (node.isExchangeNode()) {
                    ret *= 1.5;
                }
            }
        }
        */

        return ret;
    }
}
