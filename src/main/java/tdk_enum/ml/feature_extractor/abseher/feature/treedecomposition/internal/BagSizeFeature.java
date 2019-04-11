package tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition.internal;

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
public class BagSizeFeature extends TreeDecompositionFeature {
    
    public BagSizeFeature() {
        super("BagSize");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IMLGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            List<Double> values = 
                new ArrayList<>();
            
            if (!td.isEmpty()) {
                for (DecompositionNode node : td.getNodeList()) {
                    if (node != null) {
                        values.add((double)node.getItemCount());
                    }
                }
            }
            
            ret = new StatisticsSummary(values);
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, false);
    }
    
}
