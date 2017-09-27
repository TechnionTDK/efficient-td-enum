package tdenum.common.IO.result_handler;

import tdenum.common.IO.WhenToPrint;
import tdenum.graph.graphs.IGraph;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;

import java.io.PrintWriter;

public interface IResultHandler {

    void newResult(final IChordalGraph triangulation);

    void setGraph(IGraph graph);

    void setDetailedOutput(PrintWriter detailedOutput);

    void setSummaryFile(PrintWriter summaryFile);

    void setWhenToPrint(WhenToPrint whenToPrint);


    void setStartTime(long startTime);

    void setEndTime(long endTime);

    void setTimeLimitExeeded(boolean timeLimitExeeded);

    void setAlgorithm(String algorithm);

    void setSeparators(int separators);

    int getResultsFound();

    void createDetailedOutput();

    void createSummaryFile();

    void printSummaryTable();


}
