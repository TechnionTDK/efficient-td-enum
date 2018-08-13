package tdk_enum.common.IO.result_handler.minimal_seperator.parallel;

import tdk_enum.common.IO.result_handler.minimal_seperator.AbstractMinimalSeperatorResultHandler;
import tdk_enum.common.IO.result_handler.minimal_seperator.MinimalSeperatorResultInformation;
import tdk_enum.common.IO.result_handler.minimal_seperator.single_thread.SingleThreadMinimalSeperatorResultHandler;
import tdk_enum.graph.data_structures.MinimalSeparator;

import java.util.concurrent.atomic.AtomicInteger;

public class ParallelMinimalSperatorResultHandler extends SingleThreadMinimalSeperatorResultHandler {
    AtomicInteger atomiceResultsFound = new AtomicInteger();

    @Override
    public void newResult(MinimalSeparator result) {
        MinimalSeperatorResultInformation resultInformation = new MinimalSeperatorResultInformation(atomiceResultsFound.incrementAndGet(), getTime());

    }

    @Override
    public void printSummaryTable()
    {
        resultsFound = atomiceResultsFound.intValue();
        super.printSummaryTable();
    }
}
