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
public class GraphEccentricityFeature extends GraphFeature {

    public GraphEccentricityFeature() {
        super("GraphEccentricity");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IMLGraph instance) {
        StatisticsSummary ret = null;

        if (instance != null) {
            if (instance.isConnected()) {
                List<Double> values =
                        new ArrayList<>();

                for (Node vertex : instance.accessVertices())
                {
                    double eccentricity =
                            instance.getEccentricity(vertex);

                    values.add(eccentricity);
                }

                ret = new StatisticsSummary(values);
            }
            else {
                List<Double> values =
                        new ArrayList<>();

                for (int i = 0; i < instance.getNumberOfNodes(); i++)
                {
                    values.add(Double.POSITIVE_INFINITY);
                }

                ret = new StatisticsSummary(values);
            }
        }

        return FeatureMeasurement.getInstance(super.getName(), ret, false);
    }

}