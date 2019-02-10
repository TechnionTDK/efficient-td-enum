package tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.internal;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.DecompositionNode;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.TreeDecomposition;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;
import tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.TreeDecompositionFeature;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class BagNeighborhoodCoverageFactorFeature extends TreeDecompositionFeature {
    
    public BagNeighborhoodCoverageFactorFeature() {
        super("BagNeighborhoodCoverageFactor");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            List<Double> values = 
                new ArrayList<>();
            
            if (!td.isEmpty()) {
                for (DecompositionNode node : td.getNodeList()) {
                    if (node != null && node.getItemCount() > 0) {
                        values.add(getNeighborhoodCoverageFactor(instance, node));
                    }
                }
            }
            
            ret = new StatisticsSummary(values);
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, false);
    }
    
    private double getNeighborhoodCoverageFactor(IGraph instance, DecompositionNode node) {
        double ret = 0.0;
        
        if (instance != null && node != null) {
            if (node.getItemCount() > 0) {
                int count = 0;
                
                for (Node item : node.accessItemList()) {
                    count++;

                    int neighborCount = 
                        instance.accessNeighbors(item).size();
                    
                    if (neighborCount > 0) {
                        int bagNeighborCount =
                            instance.getNeighbors(item, node).size();
                    
                        ret += (double)bagNeighborCount / neighborCount;
                    }
                    else {
                        ret += 1.0;
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
