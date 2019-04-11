package tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.internal;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.IMLGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;
import tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.TreeDecompositionFeature;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class ForgottenVertexConnectednessFactorFeature extends TreeDecompositionFeature {
    
    public ForgottenVertexConnectednessFactorFeature() {
        super("ForgottenVertexConnectednessFactor");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IMLGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            List<Double> values = 
                new ArrayList<>();
            
            if (!td.isEmpty()) {
                for (DecompositionNode node : td.getForgetNodeList()) {
                    if (node != null) {
                        values.add(getConnectednessFactor(instance, node));
                    }
                }
            }
            
            ret = new StatisticsSummary(values);
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, false);
    }
    
    private double getConnectednessFactor(IMLGraph instance, DecompositionNode node) {
        double ret = 0.0;
        
        if (instance != null && node != null) {
            if (node.getForgottenItemList().size() > 0) {
                int count = 0;
                
                for (Node item1 : node.getForgottenItemList()) {
                    for (Node item2 : node.accessItemList()) {
                        if (item1 != item2) {
                            count++;
                            
                            if (instance.isConnected(item1, item2)) {
                                ret++;
                            }
                        }
                    }
                }
                
                if (count > 0) {
                    ret /= count;
                }
            }
            else {
                ret = 0.0;
            }
        }
        
        return ret;
    }
    
}
