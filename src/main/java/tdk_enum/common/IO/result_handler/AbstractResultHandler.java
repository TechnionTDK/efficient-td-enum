package tdk_enum.common.IO.result_handler;

import tdk_enum.common.IO.result_handler.chordal_graph.ChordalGraphResultInformation;
import tdk_enum.common.configuration.config_types.EnumerationType;
import tdk_enum.common.configuration.config_types.RunningMode;
import tdk_enum.common.configuration.config_types.WhenToPrint;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.graph.graphs.IGraph;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import static tdk_enum.common.IO.CSVOperations.dataToCSV;
import static tdk_enum.common.configuration.config_types.EnumerationType.SEPARATORS;
import static tdk_enum.common.configuration.config_types.RunningMode.SINGLE_THREAD;

public abstract class AbstractResultHandler<EnumType> implements IResultHandler<EnumType>{

    protected IGraph graph;

    protected PrintWriter detailedOutput;
    protected PrintWriter summaryOutput;
    protected WhenToPrint whenToPrint;

    protected ResultInformation firstResult;
    protected String fileNameAddition = "";

    protected boolean timeLimitExeeded = false;



    protected int resultsFound = 0;
    protected boolean print = false;

    protected long startTime  = 0;
    protected double endTime = 0;


    protected EnumerationType enumerationType = null;



    protected RunningMode runningMode = null;

    public String getEnumeratorType() {
        return enumeratorType;
    }

    public String getField() {
        return field;
    }

    @Override
    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getGraphName() {
        return graphName;
    }

    @Override
    public void setGraphName(String graphName) {
        this.graphName = graphName;
    }

    public int getNodes() {
        return nodes;
    }

    @Override
    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    public int getEdges() {
        return edges;
    }

    @Override
    public void setEdges(int edges) {
        this.edges = edges;
    }

    protected String field = "";

    protected String type = "";

    protected String graphName = "";

    protected  int nodes = 0;

    protected  int edges = 0;


    @Override
    public void setEnumeratorType(String enumeratorType) {
        this.enumeratorType = enumeratorType;
    }

    protected String enumeratorType = "";

    @Override
    public String getFileNameAddition() {
        return fileNameAddition;
    }

    @Override
    public void setFileNameAddition(String fileNameAddition) {
        this.fileNameAddition = fileNameAddition;
    }




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
        this.endTime = ((double)endTime)/1000;
    }

    @Override
    public void setTimeLimitExeeded(boolean timeLimitExeeded) {
        this.timeLimitExeeded = timeLimitExeeded;
    }

    public EnumerationType getEnumerationType() {
        return enumerationType;
    }

    @Override
    public void setEnumerationType(EnumerationType enumerationType) {
        this.enumerationType = enumerationType;
    }

    public RunningMode getRunningMode() {
        return runningMode;
    }

    @Override
    public void setRunningMode(RunningMode runningMode) {
        this.runningMode = runningMode;
    }



    @Override
    public int getResultsFound() {
        return resultsFound;
    }

    protected String summaryGeneralHeaders = dataToCSV("Field", "Type", "Graph", "Nodes", "Edges", "Finished",
            "Enumeration Type", "Running Mode", "Enumerator Type" ,"Time");

    protected String summaryHeaderSpecificFields =  "";




    protected void printSummaryHeader()
    {
        try {
            String headers = dataToCSV(summaryGeneralHeaders,getSummaryHeaderSpecificFields());
            summaryOutput.println(headers);
            summaryOutput.flush();
        }
         catch (Exception e)
         {
             e.printStackTrace();
         }

    }

    protected double getTime()
    {
        return ((double) (TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-startTime )))/1000;
    }




    protected String getDataToCSV()
    {
        String summary = dataToCSV(
                field,type,graphName, nodes, edges,
                timeLimitExeeded? "NO" : "YES",
                enumerationType, runningMode, enumeratorType,
                endTime);
        return summary;
    }

    protected abstract  String getSummaryHeaderSpecificFields();



}
