package tdk_enum.common.IO.result_handler.chordal_graph;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import tdk_enum.common.IO.result_handler.AbstractResultHandler;
import tdk_enum.common.configuration.config_types.OutputType;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import static tdk_enum.common.IO.CSVOperations.dataToCSV;

public abstract class AbstractChordalGraphResultHandler extends AbstractResultHandler<IChordalGraph> implements IChordalGraphResultHandler {

    protected String algorithm;

    protected int separators;

    protected ChordalGraphResultInformation minWidthResult;
    protected ChordalGraphResultInformation minFillResult;
    protected ChordalGraphResultInformation minBagExpSizeResult;
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

    protected String summaryHeaderSpecificFields =  dataToCSV("Algorithm", "Separators generated","Results","First Width","Min Width","Max Width",
                                                                          "Best Width Time","Best Width Count","Good width Count","First Fill","Min Fill","Max Fill","Best Fill Time",
                                                                          "Best Fill Count","Good Fill Count","First ExpBags","Min ExpBags","Max ExpBags","Best ExpBags Time");

    @Override
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void setSeparators(int separators) {
        this.separators = separators;
    }




    @Override
    public  void createDetailedOutput()
    {
        detailedOutput = null;
        StringBuilder sb = new StringBuilder().append(TDKEnumFactory.getGraphField()).append(".").
                append(TDKEnumFactory.getGraphType()).append(".").
                append(TDKEnumFactory.getGraphName()).append(".").
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
        if (outputType == OutputType.CSV){
            File summaryFile = new File("chordal graphs summary"+ (fileNameAddition.equals("") ? "" : ("_"+fileNameAddition) )+".csv");
            summaryOutput = null;
            if (!summaryFile.exists()) {
                try {
                    summaryOutput = new PrintWriter(summaryFile);
                    printSummaryHeader();
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


    }


    @Override
    public void printSummaryTable()
    {
        switch (outputType){
            case CSV:{
                printCSV();
                break;
            }
            case MONGODB:{
                printMongoDB();
                break;
            }
        }



    }

    protected  void printCSV()
    {
        String summary = dataToCSV(
                super.getDataToCSV(),
                algorithm,
                separators,
                resultsFound,
                firstWidth,
                minWidth,
                maxWidth,
                minWidthResult!= null? minWidthResult.getTime() : 0,
                minWidthCount,
                goodWidthCount,
                firstFill,
                minFill,
                maxFill,
                minFillResult!=null ?minFillResult.getTime(): 0,
                minFillCount,
                goodFillCount,
                firstResult!=null ? ((ChordalGraphResultInformation)firstResult).getExpBagSize() : 0,

                minBagExpSize,
                maxBagExpSize,
                minBagExpSizeResult!=null ? minBagExpSizeResult.getTime() : 0);
        summaryOutput.println(summary);
        summaryOutput.close();

    }

    protected void  printMongoDB(){
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));

    }

    @Override
    public void print(IChordalGraph result) {
        newResult(result);
    }


    @Override
    protected  String getSummaryHeaderSpecificFields()
    {
        return dataToCSV("Algorithm", "Separators generated","Results","First Width","Min Width","Max Width",
                "Best Width Time","Best Width Count","Good width Count","First Fill","Min Fill","Max Fill","Best Fill Time",
                "Best Fill Count","Good Fill Count","First ExpBags","Min ExpBags","Max ExpBags","Best ExpBags Time");

    }

}
