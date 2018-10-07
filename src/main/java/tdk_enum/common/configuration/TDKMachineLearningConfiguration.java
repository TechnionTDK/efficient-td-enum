package tdk_enum.common.configuration;

import tdk_enum.common.configuration.config_types.*;

import static tdk_enum.common.configuration.config_types.MLModelType.OMNI;

public class TDKMachineLearningConfiguration extends TDKNiceTreeDecompositionEnumConfiguration {

    public TDKMachineLearningConfiguration()
    {
        parallelMISEnumeratorType = ParallelMISEnumeratorType.NESTED;
        enumerationType = EnumerationType.NICE_TD;
        runningMode = RunningMode.PARALLEL;
        separatorsGraphType = SeparatorsGraphType.DEMON;
    }

    String inputPath = "";

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    String modelStorePath = "";

    String modelLoadPath = "";

    MLModelType mlModelType = OMNI;


}
