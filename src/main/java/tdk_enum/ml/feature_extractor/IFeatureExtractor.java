package tdk_enum.ml.feature_extractor;

import tdk_enum.graph.graphs.IMLGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.ml.classifiers.common.DecompositionDetails;
import tdk_enum.ml.feature_extractor.abseher.feature.BenchmarkRun;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;

import java.io.File;
import java.util.Map;

public interface IFeatureExtractor {

    FeatureExtractionResult getFeatures(int tdID, ITreeDecomposition treeDecomposition, IMLGraph graph);
    FeatureExtractionResult getFeatures(int tdID, ITreeDecomposition treeDecomposition, IMLGraph graph, BenchmarkRun benchmarkRun);
//    Map<String, Double> getFlatFeatures(int tdID, ITreeDecomposition treeDecomposition, IGraph graph);
//    Map<String, Double> getFlatFeatures(int tdID, ITreeDecomposition treeDecomposition, IGraph graph, BenchmarkRun benchmarkRun);
    void toCSV(File csv, FeatureExtractionResult features);
    void toCSV (File csv, Map<String, Double> flatFeatures);

    String prepareCSV(String rawCSV, String output);

    void toCSV(File csv, DecompositionDetails decompositionDetails);
}
