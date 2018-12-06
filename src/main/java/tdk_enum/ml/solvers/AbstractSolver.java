package tdk_enum.ml.solvers;

import tdk_enum.common.configuration.config_types.MLProblemType;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSolver implements  ISolver{

    long timeLimit;
    long memeoryLimit;
    List<Integer> permitedErrorCodes = new ArrayList<>();
    MLProblemType mlProblemType;

    public List<Integer> getPermitedErrorCodes() {
        return permitedErrorCodes;
    }

    public void setPermitedErrorCodes(List<Integer> permitedErrorCodes) {
        this.permitedErrorCodes = permitedErrorCodes;
    }

    @Override
    public MLProblemType getMlProblemType() {
        return mlProblemType;
    }

    @Override
    public void setMlProblemType(MLProblemType mlProblemType) {
        this.mlProblemType = mlProblemType;
    }

    @Override
    public long getTimeLimit() {
        return timeLimit;
    }

    @Override
    public void setTimeLimit(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    @Override
    public long getMemeoryLimit() {
        return memeoryLimit;
    }


    @Override
    public void setMemeoryLimit(long memeoryLimit) {
        this.memeoryLimit = memeoryLimit;
    }
}
