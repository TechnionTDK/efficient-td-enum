package tdk_enum.common.configuration;

import tdk_enum.common.configuration.config_types.*;

import static tdk_enum.common.configuration.config_types.MLClassifierType.WEKA;
import static tdk_enum.common.configuration.config_types.MLFeatureExtractor.ABESEHER;
import static tdk_enum.common.configuration.config_types.MLModelInput.FILES;
import static tdk_enum.common.configuration.config_types.MLModelType.COMBINED;
import static tdk_enum.common.configuration.config_types.MLProblemType.DFLAT_3COL;
import static tdk_enum.common.configuration.config_types.MLSortTD.NO;

public class TDKMLConfiguration extends TDKTreeDecompositionEnumConfiguration {

    public TDKMLConfiguration()
    {
        parallelMISEnumeratorType = ParallelMISEnumeratorType.NESTED;
        enumerationType = EnumerationType.SAVE_NICE_TD;
        runningMode = RunningMode.PARALLEL;
        separatorsGraphType = SeparatorsGraphType.DEMON;
    }

    String datasetPath = "";

    String modelStorePath = "";

    String modelLoadPath = "";

    MLClassifierType mlClassifierType = WEKA;

    MLModelType mlModelType = COMBINED;

    MLModelInput mlModelInput = FILES;

    MLProblemType mlProblemType = DFLAT_3COL;

    MLSortTD mlSortTD = NO;

    int solverTimeLimit = 5000;

    int solverMemoryLimit = 10000;


    int tdLimitPerGraph = 400;

    MLFeatureExtractor mlFeatureExtractor = ABESEHER;

    TransformationMode transformationMode = TransformationMode.NONE;

    public int getPredictionSolverMinTime() {
        return predictionSolverMinTime;
    }

    public void setPredictionSolverMinTime(int predictionSolverMinTime) {
        this.predictionSolverMinTime = predictionSolverMinTime;
    }

    int predictionSolverMinTime = 30;



    public String getDatasetPath() {
        return datasetPath;
    }

    public void setDatasetPath(String datasetPath) {
        this.datasetPath = datasetPath;
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

    public MLModelType getMlModelType() {
        return mlModelType;
    }

    public void setMlModelType(MLModelType mlModelType) {
        this.mlModelType = mlModelType;
    }

    public MLModelInput getMlModelInput() {
        return mlModelInput;
    }

    public void setMlModelInput(MLModelInput mlModelInput) {
        this.mlModelInput = mlModelInput;
    }

    public MLProblemType getMlProblemType() {
        return mlProblemType;
    }

    public void setMlProblemType(MLProblemType mlProblemType) {
        this.mlProblemType = mlProblemType;
    }

    public int getSolverTimeLimit() {
        return solverTimeLimit;
    }

    public void setSolverTimeLimit(int solverTimeLimit) {
        this.solverTimeLimit = solverTimeLimit;
    }

    public int getSolverMemoryLimit() {
        return solverMemoryLimit;
    }

    public void setSolverMemoryLimit(int solverMemoryLimit) {
        this.solverMemoryLimit = solverMemoryLimit;
    }

    public MLFeatureExtractor getMlFeatureExtractor() {
        return mlFeatureExtractor;
    }

    public void setMlFeatureExtractor(MLFeatureExtractor mlFeatureExtractor) {
        this.mlFeatureExtractor = mlFeatureExtractor;
    }


    public int getTdLimitPerGraph() {
        return tdLimitPerGraph;
    }

    public void setTdLimitPerGraph(int tdLimitPerGraph) {
        this.tdLimitPerGraph = tdLimitPerGraph;
    }

    public MLClassifierType getMlClassifierType() {
        return mlClassifierType;
    }

    public void setMlClassifierType(MLClassifierType mlClassifierType) {
        this.mlClassifierType = mlClassifierType;
    }

    public MLSortTD getMlSortTD() {
        return mlSortTD;
    }

    public void setMlSortTD(MLSortTD mlSortTD) {
        this.mlSortTD = mlSortTD;
    }

    public TransformationMode getTransformationMode() {
        return transformationMode;
    }

    public void setTransformationMode(TransformationMode transformationMode) {
        this.transformationMode = transformationMode;
    }
}
