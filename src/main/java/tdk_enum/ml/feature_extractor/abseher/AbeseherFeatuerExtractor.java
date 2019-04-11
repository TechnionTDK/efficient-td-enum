package tdk_enum.ml.feature_extractor.abseher;

import tdk_enum.graph.graphs.IMLGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.ml.feature_extractor.AbstractFeatureExtractor;
import tdk_enum.ml.feature_extractor.abseher.feature.BenchmarkRun;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureCollection;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;

public class AbeseherFeatuerExtractor extends AbstractFeatureExtractor {
    @Override
    public FeatureExtractionResult getFeatures(int tdID, ITreeDecomposition treeDecomposition, IMLGraph graph) {
        FeatureCollection featureCollection = FeatureCollection.getDefaultFeatureCollection();
        return featureCollection.getEvaluationResult(tdID, graph, treeDecomposition);
    }

    @Override
    public FeatureExtractionResult getFeatures(int tdID, ITreeDecomposition treeDecomposition, IMLGraph graph, BenchmarkRun benchmarkRun) {
        FeatureCollection featureCollection = FeatureCollection.getDefaultFeatureCollection();
        return  featureCollection.getEvaluationResult(tdID, graph, benchmarkRun.getUserTime(),
                benchmarkRun.getSystemTime(), benchmarkRun.getWallClockTime(), benchmarkRun.getMemoryConsumption(),
                benchmarkRun.isMemoryError(), benchmarkRun.isTimeoutError(), benchmarkRun.isExitCodeError(),
                treeDecomposition);
    }

//    @Override
//    public Map<String, Double> getFlatFeatures(int tdID, ITreeDecomposition treeDecomposition, IGraph graph) {
//        return getFeatures(tdID, treeDecomposition, graph).toFlatMap();
//    }
//
//    @Override
//    public Map<String, Double> getFlatFeatures(int tdID, ITreeDecomposition treeDecomposition, IGraph graph, BenchmarkRun benchmarkRun) {
//        return getFeatures(tdID, treeDecomposition, graph, benchmarkRun).toFlatMap();
//    }


}
