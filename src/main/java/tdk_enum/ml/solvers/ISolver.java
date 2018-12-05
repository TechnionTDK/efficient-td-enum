package tdk_enum.ml.solvers;

import tdk_enum.common.configuration.config_types.MLProblemType;
import tdk_enum.ml.solvers.execution.CommandResult;

import java.io.File;

public interface ISolver {

    CommandResult solve(File graphFile, File tdFile, MLProblemType mlProblemType);

    long getTimeLimit();

    void setTimeLimit(long timeLimit);

    long getMemeoryLimit();

    void setMemeoryLimit(long memeoryLimit);
}
