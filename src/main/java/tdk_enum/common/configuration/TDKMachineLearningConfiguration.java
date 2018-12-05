package tdk_enum.common.configuration;

import tdk_enum.common.configuration.config_types.*;

import static tdk_enum.common.configuration.config_types.MLFeatureExtractor.ABESEHER;
import static tdk_enum.common.configuration.config_types.MLModelInput.FILES;
import static tdk_enum.common.configuration.config_types.MLModelType.OMNI;
import static tdk_enum.common.configuration.config_types.MLProblemType.DFLAT_3COL;

public class TDKMachineLearningConfiguration extends TDKTreeDecompositionEnumConfiguration {

    public TDKMachineLearningConfiguration()
    {
        parallelMISEnumeratorType = ParallelMISEnumeratorType.NESTED;
        enumerationType = EnumerationType.NICE_TD;
        runningMode = RunningMode.PARALLEL;
        separatorsGraphType = SeparatorsGraphType.DEMON;
    }

    String inputPath = "";

    String modelStorePath = "";

    String modelLoadPath = "";

    MLModelType mlModelType = OMNI;

    MLModelInput mlModelInput = FILES;

    MLProblemType mlProblemType = DFLAT_3COL;

    int timeLimit = 5000;

    int memoryLimit = 10000;

    MLFeatureExtractor mlFeatureExtractor = ABESEHER;

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }


}
