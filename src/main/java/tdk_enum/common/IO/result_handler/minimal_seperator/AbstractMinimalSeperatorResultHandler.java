package tdk_enum.common.IO.result_handler.minimal_seperator;

import tdk_enum.common.IO.result_handler.AbstractResultHandler;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.graph.data_structures.MinimalSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import static tdk_enum.common.IO.CSVOperations.dataToCSV;

public abstract class AbstractMinimalSeperatorResultHandler extends AbstractResultHandler<MinimalSeparator> implements IMinimalSeperatorResultHandler {




    protected int separators;



    public void setSeparators(int separators)
    {
        this.separators = separators;
    }

    @Override
    public void createSummaryFile()
    {
        File summaryFile = new File("minimal separators summary"+ (fileNameAddition.equals("") ? "" : ("_"+fileNameAddition) )+".csv");
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
                separators);
        summaryOutput.println(summary);
        summaryOutput.close();
    }

    @Override
    public void print(MinimalSeparator result) {
        newResult(result);
    }



    @Override
    public  void createDetailedOutput()
    {
        detailedOutput = null;
        StringBuilder sb = new StringBuilder().append(TDKEnumFactory.getGraphField()).append(".").
                append(TDKEnumFactory.getGraphType()).append(".").
                append(TDKEnumFactory.getGraphName()).append(".");

        File file = new File(sb.toString());
        try {
            detailedOutput = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected  String getSummaryHeaderSpecificFields()
    {
        return dataToCSV("separators");
    }

}
