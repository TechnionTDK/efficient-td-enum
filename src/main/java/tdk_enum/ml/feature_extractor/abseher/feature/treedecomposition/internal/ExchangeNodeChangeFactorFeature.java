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
public class ExchangeNodeChangeFactorFeature extends TreeDecompositionFeature {
    
    public ExchangeNodeChangeFactorFeature() {
        super("ExchangeNodeChangeFactor");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IMLGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            List<Double> values = 
                new ArrayList<>();
            
            if (!td.isEmpty()) {
                for (DecompositionNode node : td.getExchangeNodeList()) {
                    if (node != null) {
                        double forgetCount = 
                            node.getForgottenItemList().size();
                        
                        double introduceCount = 
                            node.getIntroducedItemList().size();
                        
                        if (forgetCount < introduceCount) {
                            values.add(forgetCount / introduceCount);
                        }
                        else {
                            values.add(introduceCount / forgetCount);
                        }
                    }
                }
            }
            
            ret = new StatisticsSummary(values);
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, false);
    }
    
}
