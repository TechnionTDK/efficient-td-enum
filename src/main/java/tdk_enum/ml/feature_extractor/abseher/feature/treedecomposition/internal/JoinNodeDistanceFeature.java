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
public class JoinNodeDistanceFeature extends TreeDecompositionFeature {
    
    public JoinNodeDistanceFeature() {
        super("JoinNodeDistance");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IMLGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            List<Double> values = 
                new ArrayList<>();
            
            if (!td.isEmpty()) {
                List<DecompositionNode> joinNodes =
                    new ArrayList<>();
        
                for (DecompositionNode node : td.getJoinNodeList()) {
                    if (node != null) {
                        if (!joinNodes.contains(node)) {
                            joinNodes.add(node);
                        }
                    }
                }
                
                if (joinNodes.size() > 1) {
                    for (DecompositionNode node1 : joinNodes) {
                        if (node1 != null) {
                            for (DecompositionNode node2 : joinNodes) {
                                if (node2 != null && !node2.equals( node1)) {
                                    values.add((double)td.getDistance(node1, node2));
                                }
                            }
                        }
                    }
                }
                else
                {
                    if (joinNodes.size()==1)
                    {
                        values.add(0.0);
                    }
                }
            }
            
            ret = new StatisticsSummary(values);
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, false);
    }
    
}
