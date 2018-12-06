package tdk_enum.ml;

import tdk_enum.common.IO.InputFile;
import tdk_enum.common.configuration.config_types.MLModelInput;
import tdk_enum.common.configuration.config_types.MLSortTD;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.enumerators.tree_decomposition.ITreeDecompositionEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.ml.classifiers.IClassifier;
import tdk_enum.ml.feature_extractor.IFeatureExtractor;
import tdk_enum.ml.solvers.ISolver;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TDMLRunner {

    ITreeDecompositionEnumerator enumerator;
    ISolver solver;
    IFeatureExtractor featureExtractor;
    IClassifier classifier;
    int tdLimit;

    MLModelInput mlModelInput;


    String inputPath = "";

    String modelStorePath = "";

    MLSortTD mlSortTD;

    public MLSortTD getMlSortTD() {
        return mlSortTD;
    }

    public void setMlSortTD(MLSortTD mlSortTD) {
        this.mlSortTD = mlSortTD;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getModelStorePath() {
        return modelStorePath;
    }

    public void setModelStorePath(String modelStorePath) {
        this.modelStorePath = modelStorePath;
    }

    public String getModelLoadPath() {
        return modelLoadPath;
    }

    public void setModelLoadPath(String modelLoadPath) {
        this.modelLoadPath = modelLoadPath;
    }

    String modelLoadPath = "";

    public MLModelInput getMlModelInput() {
        return mlModelInput;
    }

    public void setMlModelInput(MLModelInput mlModelInput) {
        this.mlModelInput = mlModelInput;
    }



    public ITreeDecompositionEnumerator getEnumerator() {
        return enumerator;
    }

    public void setEnumerator(ITreeDecompositionEnumerator enumerator) {
        this.enumerator = enumerator;
    }

    public ISolver getSolver() {
        return solver;
    }

    public void setSolver(ISolver solver) {
        this.solver = solver;
    }

    public IFeatureExtractor getFeatureExtractor() {
        return featureExtractor;
    }

    public void setFeatureExtractor(IFeatureExtractor featureExtractor) {
        this.featureExtractor = featureExtractor;
    }

    public IClassifier getClassifier() {
        return classifier;
    }

    public void setClassifier(IClassifier classifier) {
        this.classifier = classifier;
    }

    public int getTdLimit() {
        return tdLimit;
    }

    public void setTdLimit(int tdLimit) {
        this.tdLimit = tdLimit;
    }

    public void trainByDataSet(List<String> inputs)
    {
        for (String filePath : inputs)
        {
            InputFile inputFile = new InputFile(filePath);
            TDKEnumFactory.init(inputFile);

        }

    }

    public void trainByInput(InputFile inputFile, Set<IChordalGraph> chordalGraphs)
    {

    }

    public void trainByCSV()
    {

    }

    public void loadTrainedModels()
    {

    }

    void train()
    {

    }

//    public Map<String, Double> predict(ITreeDecomposition td)
//    {
//
//    }

}
