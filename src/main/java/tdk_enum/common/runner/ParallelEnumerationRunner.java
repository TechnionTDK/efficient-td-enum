package tdk_enum.common.runner;

import java.util.concurrent.TimeUnit;

public class ParallelEnumerationRunner extends AbstractEnumerationRunner {
    @Override
    public Object call() throws Exception {

        long startTime = System.nanoTime();
        resultHandler.setStartTime(startTime);
        while(minimalTriangulationsEnumerator.hasNext() && !Thread.currentThread().isInterrupted())
        {
            minimalTriangulationsEnumerator.next();
        }

        long finishTime = System.nanoTime() - startTime;

        resultHandler.setEndTime(TimeUnit.NANOSECONDS.toSeconds(finishTime));
        return null;
    }
}
