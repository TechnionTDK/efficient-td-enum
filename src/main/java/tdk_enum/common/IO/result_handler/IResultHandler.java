package tdk_enum.common.IO.result_handler;

import tdk_enum.common.IO.WhenToPrint;
import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;

import java.io.PrintWriter;

public interface IResultHandler extends IResultPrinter<IChordalGraph>{

    void newResult(final IChordalGraph triangulation);

    String getFileNameAddition();

    void setFileNameAddition(String fileNameAddition);

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
