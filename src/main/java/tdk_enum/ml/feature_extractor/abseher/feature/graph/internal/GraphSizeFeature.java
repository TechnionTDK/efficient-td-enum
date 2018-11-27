package tdk_enum.ml.feature_extractor.abseher.feature.graph.internal;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;
import tdk_enum.ml.feature_extractor.abseher.feature.graph.GraphFeature;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class GraphSizeFeature extends GraphFeature {

    public GraphSizeFeature() {
        super("GraphSize");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IGraph instance) {
        StatisticsSummary ret = null;

        if (instance != null) {
            double value =
                    instance.getNumberOfNodes();

            ret = new StatisticsSummary(value);
        }

        return FeatureMeasurement.getInstance(super.getName(), ret, true);
    }

}