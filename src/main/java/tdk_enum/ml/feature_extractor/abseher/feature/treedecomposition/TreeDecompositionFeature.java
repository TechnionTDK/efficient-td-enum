package tdk_enum.ml.feature_extractor.abseher.feature.treedecomposition;

import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.tree_decomposition.single_thread.TreeDecomposition;
import tdk_enum.ml.feature_extractor.abseher.feature.Feature;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureMeasurement;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public abstract class TreeDecompositionFeature extends Feature {

    public TreeDecompositionFeature(String name) {
        super(name);
    }

    public abstract FeatureMeasurement extractMeasurement(IGraph instance, TreeDecomposition td);

}
