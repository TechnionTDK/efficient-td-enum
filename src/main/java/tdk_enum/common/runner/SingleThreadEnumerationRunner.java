package tdk_enum.common.runner;

import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;

import java.util.concurrent.TimeUnit;

public class SingleThreadEnumerationRunner extends AbstractEnumerationRunner {


    @Override
    public Object call() throws Exception {

        long startTime = System.nanoTime();
        resultHandler.setStartTime(startTime);
        while(minimalTriangulationsEnumerator.hasNext() && !Thread.currentThread().isInterrupted())
        {
            IChordalGraph result = minimalTriangulationsEnumerator.next();
            resultHandler.newResult(result);
//            minimalTriangulationsEnumerator.next();

        }
        long finishTime = System.nanoTime() - startTime;

        resultHandler.setEndTime(TimeUnit.NANOSECONDS.toSeconds(finishTime));
        return null;
    }
}
