package tdenum.common.IO.result_handler;

import tdenum.common.IO.WhenToPrint;
import tdenum.factories.TDEnumFactory;
import tdenum.graph.graphs.IGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import static tdenum.common.IO.CSVOperations.dataToCSV;

public abstract class AbstractResultHandler implements IResultHandler{

    protected IGraph graph;

    protected PrintWriter detailedOutput;
    protected PrintWriter summaryOutput;
    protected WhenToPrint whenToPrint;

    protected ResultInformation firstResult;
    protected ResultInformation minWidthResult;
    protected ResultInformation minFillResult;
    protected ResultInformation minBagExpSizeResult;
    protected int minWidth = 0;
    protected int maxWidth = 0;
    protected int minFill = 0;
    protected int maxFill = 0;
    protected long minBagExpSize = 0;
    protected long maxBagExpSize = 0;
    protected int firstWidth = 0;
    protected int firstFill = 0;
    protected int minWidthCount = 0;
    protected int minFillCount = 0;
    protected int goodWidthCount = 0;
    protected int goodFillCount = 0;
    protected int resultsFound = 0;
    protected boolean print = false;

    long startTime  = 0;
    long endTime = 0;



    boolean timeLimitExeeded = false;
    String algorithm;

    int separators;


    @Override
    public void setGraph(IGraph graph) {
        this.graph = graph;
    }

    @Override
    public void setDetailedOutput(PrintWriter detailedOutput) {
        this.detailedOutput = detailedOutput;
    }

    @Override
    public void setSummaryFile(PrintWriter summaryFile) {
        this.summaryOutput = summaryFile;
    }
    @Override
    public void setWhenToPrint(WhenToPrint whenToPrint) {
        this.whenToPrint = whenToPrint;
    }

    @Override
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public void setTimeLimitExeeded(boolean timeLimitExeeded) {
        this.timeLimitExeeded = timeLimitExeeded;
    }

    @Override
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void setSeparators(int separators) {
        this.separators = separators;
    }

    @Override
    public int getResultsFound() {
        return resultsFound;
    }

    static String summaryGeneralHeaders = dataToCSV("Field", "Type", "Graph", "Nodes", "Edges", "Finished Time",
            "Algorithm", "Separators generated");

    static String summaryHeaderSpecificFields = dataToCSV("Results","First Width","Min Width","Max Width",
            "Best Width Time","Best Width Count","Good width Count","First Fill","Min Fill","Max Fill","Best Fill Time",
            "Best Fill Count","Good Fill Count","First ExpBags","Min ExpBags","Max ExpBags","Best ExpBags Time");

    @Override
    public  void createDetailedOutput()
    {
        detailedOutput = null;
        StringBuilder sb = new StringBuilder().append(TDEnumFactory.getGraphField()).append(".").
                                                append(TDEnumFactory.getGraphType()).append(".").
                                                append(TDEnumFactory.getGraphName()).append(".").
                                                 append(algorithm).append(".csv");
        File file = new File(sb.toString());
        try {
            detailedOutput = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createSummaryFile()
    {
        File summaryFile = new File("summary.csv");
        summaryOutput = null;
        if (!summaryFile.exists()) {
            try {
                summaryOutput = new PrintWriter(summaryFile);
                printSummaryHeader(summaryOutput);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                summaryOutput = new PrintWriter(new FileOutputStream(summaryFile, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }


    static void printSummaryHeader(PrintWriter summaryOutput)
    {
        summaryOutput.println(dataToCSV(summaryGeneralHeaders,summaryHeaderSpecificFields));
    }

    protected double getTime()
    {
        return ((double) (TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-startTime )))/1000;
    }


    @Override
    public void printSummaryTable()
    {
        String summary = dataToCSV(
                TDEnumFactory.getGraphField(),
                TDEnumFactory.getGraphType(),
                TDEnumFactory.getGraphName(),
                TDEnumFactory.getGraph().getNumberOfNodes(),
                TDEnumFactory.getGraph().getNumberOfEdges(),
                timeLimitExeeded? "Yes" : "No",
                endTime,
                algorithm,
                separators,
                resultsFound,
                firstWidth,
                minWidth,
                maxWidth,
                minWidthResult.getTime(),
                minWidthCount,
                goodWidthCount,
                firstFill,
                minFill,
                maxFill,
                minFillResult.getTime(),
                minFillCount,
                goodFillCount,
                firstResult.getExpBagSize(),
                minBagExpSize,
                maxBagExpSize,
                minBagExpSizeResult.getTime());
        summaryOutput.println(summary);
        summaryOutput.close();
    }

}
