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
public class BalancednessFactorFeature extends TreeDecompositionFeature {
    
    public BalancednessFactorFeature() {
        super("BalancednessFactor");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IGraph instance, ITreeDecomposition td) {
        StatisticsSummary ret = null;
        
        if (td != null) {
            double factor = 0.0;
            
            TreeDecompositionFeature leafNodeDepthFeature = 
                new LeafNodeDepthFeature();
            
            FeatureMeasurement measurement =
                leafNodeDepthFeature.extractMeasurement(instance, td);
            
            if (measurement != null) {
                StatisticsSummary summary = 
                    measurement.getMeasurementResult();

                if (summary != null) {
                    int count = 0;

                    if (summary.getActiveCount() > 0) {
                        double avg = 
                            summary.getAverage();

                        for (DecompositionNode node : td.getLeafNodeList()) {
                            if (node != null) {
                                double depth = node.getDepth();

                                factor += Math.abs(depth - avg);

                                count++;
                            }
                        }

                        factor /= count;
                            
                        factor /= avg;

                        ret = new StatisticsSummary(1 - factor);
                    }
                    else {
                        ret = new StatisticsSummary(Double.NaN);
                    }
                }
                else {
                    ret = new StatisticsSummary(Double.NaN);
                }    
            }
            else {
                ret = new StatisticsSummary(Double.NaN);
            }            
        }
        
        return FeatureMeasurement.getInstance(super.getName(), ret, true);
    }
    
}
