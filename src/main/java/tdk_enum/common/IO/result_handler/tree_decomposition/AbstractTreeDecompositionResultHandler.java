package tdk_enum.common.IO.result_handler.tree_decomposition;

import tdk_enum.common.IO.GraphMLPrinter;
import tdk_enum.common.IO.result_handler.AbstractResultHandler;
import tdk_enum.common.IO.result_handler.chordal_graph.AbstractChordalGraphResultHandler;
import tdk_enum.common.IO.result_handler.chordal_graph.ChordalGraphResultInformation;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static tdk_enum.common.IO.CSVOperations.dataToCSV;

public abstract class AbstractTreeDecompositionResultHandler extends AbstractResultHandler<ITreeDecomposition> implements ITreeDecompositionResultHandler{

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

    protected Long results = (long)0;
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
        File summaryFile = new File("tree decomposition summary"+ (fileNameAddition.equals("") ? "" : ("_"+fileNameAddition) )+".csv");
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


    @Override
    public void printSummaryTable()
    {

        String summary = dataToCSV(
                super.getDataToCSV(),
                algorithm,
                separators,
                results,
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
                ((ChordalGraphResultInformation)firstResult).getExpBagSize(),

                minBagExpSize,
                maxBagExpSize,
                minBagExpSizeResult.getTime());
        summaryOutput.println(summary);
        summaryOutput.close();
    }

    @Override
    public void print(ITreeDecomposition result) {
        newResult(result);
    }


    @Override
    protected  String getSummaryHeaderSpecificFields()
    {
        return dataToCSV("Algorithm", "Separators generated","Results","First Width","Min Width","Max Width",
                "Best Width Time","Best Width Count","Good width Count","First Fill","Min Fill","Max Fill","Best Fill Time",
                "Best Fill Count","Good Fill Count","First ExpBags","Min ExpBags","Max ExpBags","Best ExpBags Time");

    }

    protected void printTd(ITreeDecomposition decomposition,  Long index)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Tree Decomposition").append(File.separator);
        sb.append(inputFile.getField()).append(File.separator);
        sb.append(inputFile.getType()).append(File.separator);
        sb.append(inputFile.getName()).append(File.separator);
        sb.append(index).append(".gml");
        GraphMLPrinter.treeDecompositionToGraphMLFile(decomposition, sb.toString() );
        sb = new StringBuilder();
        sb.append("Tree Decomposition").append(File.separator);
        sb.append(inputFile.getField()).append(File.separator);
        sb.append(inputFile.getType()).append(File.separator);
        sb.append(inputFile.getName()).append(File.separator);
        sb.append(index).append(".gr");
        File file = new File(sb.toString());

        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new FileWriter(file));
            writer.write(decomposition.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
