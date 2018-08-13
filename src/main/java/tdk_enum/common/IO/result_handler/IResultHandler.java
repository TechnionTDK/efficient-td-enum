package tdk_enum.common.IO.result_handler;

import tdk_enum.common.configuration.config_types.EnumerationType;
import tdk_enum.common.configuration.config_types.RunningMode;
import tdk_enum.common.configuration.config_types.WhenToPrint;
import tdk_enum.common.IO.result_printer.IResultPrinter;
import tdk_enum.graph.graphs.IGraph;

import java.io.PrintWriter;

public interface IResultHandler<EnumType> extends IResultPrinter<EnumType>{

    void newResult(final EnumType result);

    void setField(String field);

    void setType(String type);



    void setGraphName(String graphName);

    void setNodes(int nodes);

    void setEdges(int edges);

    void setEnumeratorType(String enumeratorType);

    String getFileNameAddition();

    void setFileNameAddition(String fileNameAddition);

    void setGraph(IGraph graph);

    void setDetailedOutput(PrintWriter detailedOutput);

    void setSummaryFile(PrintWriter summaryFile);

    void setWhenToPrint(WhenToPrint whenToPrint);


    void setStartTime(long startTime);

    void setEndTime(long endTime);

    void setTimeLimitExeeded(boolean timeLimitExeeded);


    void setEnumerationType(EnumerationType enumerationType);

    void setRunningMode(RunningMode runningMode);

    int getResultsFound();

    void createDetailedOutput();

    void createSummaryFile();

    void printSummaryTable();


}
