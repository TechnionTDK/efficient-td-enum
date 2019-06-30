package tdk_enum.common.IO.result_handler.chordal_graph;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.connection.Stream;
import org.bson.Document;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;

import java.util.*;


import java.time.LocalDate;

import tdk_enum.common.IO.result_handler.AbstractResultHandler;
import tdk_enum.common.configuration.config_types.OutputType;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;

import javax.print.Doc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println("adding to the database!");
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("datasets");
        String collection_name = new StringBuilder().append(TDKEnumFactory.getGraphField()).append(".").
                append(TDKEnumFactory.getGraphType()).toString();
        // String graph_name = new StringBuilder().append(TDKEnumFactory.getGraphName()).toString() + ".uai";
        String graph_name = new StringBuilder().append(TDKEnumFactory.getGraphName()).toString();
        MongoCollection<Document> collection = database.getCollection(collection_name);

        if(collection == null){
            // TODO is this the right behavior?
            database.createCollection(collection_name);
            collection = database.getCollection(collection_name);
        }

        Document result_log_doc = new Document("TimeOfExecution", LocalDate.now())
                .append("CalculationTime", endTime)
                .append("Separators" , separators)
                .append("Triangulations" , resultsFound)
                .append("FirstWidth" , firstWidth)
                .append("MinWidth" , minWidth)
                .append("MaxWidth" , maxWidth)
                .append("BestWidthTime" , minWidthResult.getTime())
                .append("BestWidthCount" , minWidthCount)
                .append("GoodWidthCount" , goodWidthCount)
                .append("FirstFill" , firstFill)
                .append("MinFill" , minFill)
                .append("MaxFill" , maxFill)
                .append("BestFillTime" , minFillResult.getTime())
                .append("BestFillCount" , minFillCount)
                .append("GoodFillCount" , goodFillCount)
                .append("FirstExpBags" , firstResult!=null ? ((ChordalGraphResultInformation)firstResult).getExpBagSize() : 0)
                .append("MinExpBags" , minBagExpSize)
                .append("MaxExpBags" , maxBagExpSize)
                .append("BestExpBagsTime" , minBagExpSizeResult.getTime())
                .append("MSCalculationTime" , "00:00:00")
                .append("TimeErrors" , "")
                .append("CountErrors" , 0)
                .append("PMCs" , -1);

        // note: assuming here that there is AT MOST one document for each graph name.
        Document doc_to_update = collection.find(eq("GraphName", graph_name)).first();

        if(doc_to_update == null){
            // didn't find the graph in the collection, so add a new one
            IGraph graph = TDKEnumFactory.getGraph();
            Document graph_doc = new Document("GraphName", graph_name)
                    .append("Nodes", ((IGraph) graph).getNumberOfNodes())
                    .append("Edges", graph.getNumberOfEdges())
                    .append("Logs",
                            Arrays.asList(
                                    new Document("AlgorithmName", enumeratorType + "_" + algorithm)
                                            .append("Results", Arrays.asList(result_log_doc))
                            ));
            // upsert is for updating if already existing, otherwise create a new one.
            // in case multiple processess try to insert the same graph
            UpdateOptions options = new UpdateOptions().upsert(true);
            Document update_doc_set = new Document("$set", graph_doc);
            collection.updateOne(eq("GraphName", graph_name), update_doc_set, options);
            return;
        }
        ArrayList<Document> logs = new ArrayList<Document>();
        logs = doc_to_update.get("Logs", logs.getClass());
        // this replaces the last result with the new one
        ArrayList<Document> new_logs = logs.stream()
                .filter(document -> !document.get("AlgorithmName").equals(enumeratorType + "_" + algorithm))
                .collect(Collectors.toCollection(ArrayList::new));
        new_logs.add(new Document("AlgorithmName", enumeratorType + "_" + algorithm)
                .append("Results", Arrays.asList(result_log_doc)));
        //update the document
        Document updated_doc_value = new Document("Logs", new_logs);
        Document update_doc_set = new Document("$set", updated_doc_value);
        collection.updateOne(eq("GraphName", graph_name), update_doc_set);
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
