package tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.internal;



import java.util.ArrayList;
import java.util.List;

import tdk_enum.graph.data_structures.Node;
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
public class BagAdjacencyFactorFeature extends TreeDecompositionFeature {
    
    public BagAdjacencyFactorFeature() {
        super("BagAdjacencyFactor");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            List<Double> values = 
                new ArrayList<>();
            
            if (!td.isEmpty()) {
                for (DecompositionNode node : td.accessNodeList()) {
                    if (node != null && node.getItemCount() > 0) {
                        values.add(getAdjacencyFactor(instance, node));
                    }
                }
            }
            
            ret = new StatisticsSummary(values);
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, false);
    }
    
    private double getAdjacencyFactor(IGraph instance, DecompositionNode node) {
        double ret = 0.0;
        
        if (instance != null && node != null) {
            if (node.getItemCount() > 0) {
                int count = 0;
                
                for (Node item1 : node.accessItemList()) {
                    for (Node item2 : node.accessItemList()) {
                        if (item1 != item2) {
                            count++;
                            
                            if (instance.areNeighbors(item1, item2)) {
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
                ret = 1.0;
            }
        }
        
        return ret;
    }
    
}
