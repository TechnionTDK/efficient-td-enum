package tdk_enum.ml.feature_extractor.abseher.feature.graph.internal;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IMLGraph;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;
import tdk_enum.ml.feature_extractor.abseher.feature.graph.GraphFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class EdgeProbabilityFeature extends GraphFeature {

    public EdgeProbabilityFeature() {
        super("EdgeProbability");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IMLGraph instance) {
        StatisticsSummary ret = null;

        if (instance != null) {
            List<Double> values =
                    new ArrayList<>();

            double size = instance.getNumberOfNodes();

            for (Node vertex : instance.accessVertices())
            {
                double neighborCount =
                        instance.getNeighbors(vertex).size();

                values.add(neighborCount / size);
            }

            ret = new StatisticsSummary(values);
        }

        return FeatureMeasurement.getInstance(super.getName(), ret, false);
    }

}
