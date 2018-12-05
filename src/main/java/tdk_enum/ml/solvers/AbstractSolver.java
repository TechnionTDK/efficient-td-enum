package tdk_enum.ml.solvers;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSolver implements  ISolver{

    long timeLimit;
    long memeoryLimit;
    List<Integer> permitedErrorCodes = new ArrayList<>();


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
