package tdk_enum.ml.feature_extractor.abseher.feature.graph.internal;

import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;
import tdk_enum.ml.feature_extractor.abseher.feature.StatisticsSummary;
import tdk_enum.ml.feature_extractor.abseher.feature.graph.GraphFeature;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */

public class ComponentSizeFeature extends GraphFeature {

    public ComponentSizeFeature() {
        super("ComponentSize");
    }

    @Override
    public FeatureMeasurement extractMeasurement(IGraph instance) {
        StatisticsSummary ret = null;

        if (instance != null) {
            List<Double> values =
                    new ArrayList<>();

            List<Node> vertices =
                    instance.getVertices();

            while (!vertices.isEmpty()) {
                int vertex =
                        vertices.get(0).intValue();

                List<Node> component =
                        instance.accessReachableVertices(vertex);

                values.add((double)component.size() + 1);

                for (Node vertex2 : component) {
                    vertices.remove(vertex2);
                }

                vertices.remove(vertex);
            }
            ret = new StatisticsSummary(values);
        }

        return FeatureMeasurement.getInstance(super.getName(), ret, false);
    }

}
