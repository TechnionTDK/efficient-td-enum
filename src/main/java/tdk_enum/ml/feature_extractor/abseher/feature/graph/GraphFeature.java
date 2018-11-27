package tdk_enum.ml.feature_extractor.abseher.feature.graph;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.ml.feature_extractor.abseher.feature.Feature;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;

public abstract class GraphFeature extends Feature {

    public GraphFeature(String name) {
        super(name);
    }

    public abstract FeatureMeasurement extractMeasurement(IGraph instance);
}
