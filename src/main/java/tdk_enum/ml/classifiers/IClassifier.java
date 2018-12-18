package tdk_enum.ml.classifiers;

import tdk_enum.common.configuration.config_types.MLModelType;
import tdk_enum.ml.classifiers.common.DecompositionPool;
import tdk_enum.ml.classifiers.common.PredictedDecompositionPool;
import tdk_enum.ml.classifiers.common.SelectionTemplate;

import java.io.File;
import java.util.List;

public interface IClassifier {



    void trainModel(String csv, String outputPath, MLModelType mlModelType);
    void loadModels(String modelsLoadPath);


    List<SelectionTemplate> predictDecompositions(DecompositionPool decompositions,String modelLoadPath);

//    PredictedDecompositionPool predictDecompositions(DecompositionPool decompositions, File modelFile);
//    PredictedDecompositionPool predictDecompositions(DecompositionPool decompositions, List<File> modelFile);
//
//    PredictedDecompositionPool predictDecompositions(DecompositionPool decompositions, List<File> modelFiles, List<Double> weights);

//    void ItreeDecomposition (List<ITreeDecomposition> )
}
