package tdk_enum.ml.classifiers;

import tdk_enum.common.configuration.config_types.MLModelType;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import java.util.List;

public interface IClassifier {



    void trainModel(String csv, String outputPath, MLModelType mlModelType);


//    void ItreeDecomposition (List<ITreeDecomposition> )
}
