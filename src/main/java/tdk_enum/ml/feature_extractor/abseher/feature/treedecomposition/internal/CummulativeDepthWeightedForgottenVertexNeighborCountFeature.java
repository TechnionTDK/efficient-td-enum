package tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.internal;


import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.IMLGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;
import tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.TreeDecompositionFeature;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class CummulativeDepthWeightedForgottenVertexNeighborCountFeature extends TreeDecompositionFeature {
    
    public CummulativeDepthWeightedForgottenVertexNeighborCountFeature() {
        super("CummulativeDepthWeightedForgottenVertexNeighborCount");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IMLGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            double value = 0;
            
            if (!td.isEmpty()) {
                for (DecompositionNode node : td.getForgetNodeList()) {
                    if (node != null) {
                        value += getNeighborCount(instance, node) * node.getDepth();
                    }
                }
            }
            
            ret = new StatisticsSummary(value);
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, true);
    }
    
    private double getNeighborCount(IGraph instance, DecompositionNode node) {
        double ret = 0.0;
        
        if (instance != null && node != null) {
            for (Node item1 : node.getForgottenItemList()) {
                for (Node item2 : node.accessItemList()) {
                    if (item1 != item2) {
                        if (instance.areNeighbors(item1, item2)) {
                            ret++;
                        }
                    }
                }
            }
        }
        
        return ret;
    }
    
}
