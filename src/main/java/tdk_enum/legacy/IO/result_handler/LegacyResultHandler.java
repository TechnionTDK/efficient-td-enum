package tdk_enum.legacy.IO.result_handler;

import tdk_enum.common.IO.WhenToPrint;
import tdk_enum.common.IO.result_handler.ResultInformation;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;

import java.io.PrintWriter;
import java.util.Date;

import static tdk_enum.common.IO.WhenToPrint.ALWAYS;
import static tdk_enum.common.IO.WhenToPrint.NEVER;

public class LegacyResultHandler {

    IGraph graph;
    PrintWriter output;
    WhenToPrint whenToPrint;
    Date startTime = new Date();
    ResultInformation firstResult;
    ResultInformation minWidthResult;
    ResultInformation minFillResult;
    ResultInformation minBagExpSizeResult;
    int minWidth = 0;
    int maxWidth = 0;
    int minFill = 0;
    int maxFill = 0;
    long minBagExpSize = 0;
    long maxBagExpSize = 0;
    int firstWidth = 0;
    int firstFill = 0;
    int minWidthCount = 0;
    int minFillCount = 0;
    int goodWidthCount = 0;
    int goodFillCount = 0;
    int resultsFound = 0;
    boolean print = false;





    public LegacyResultHandler(final IGraph g, PrintWriter out, WhenToPrint p)
    {
       graph = g;
       output = out;
       whenToPrint = p;
    }

    double getTime()
    {
        return (new Date().getTime() - startTime.getTime())/1000;
    }

    public void newResult(final IChordalGraph triangulation)
    {
        ++resultsFound;
        ResultInformation currentResult = new ResultInformation(resultsFound, getTime(), graph, triangulation);
        print = false;



        if(resultsFound == 1)
        {
            minBagExpSizeResult = minFillResult = minWidthResult = firstResult = currentResult;
            firstWidth = minWidth = maxWidth = currentResult.getWidth();
            firstFill = minFill = maxFill = currentResult.getFill();
            minBagExpSize = maxBagExpSize = currentResult.getExpBagSize();
            goodWidthCount = minWidthCount = 1;
            goodFillCount = minFillCount = 1;
            if(whenToPrint != NEVER)
            {
                ResultInformation.printCsvHeaderByTime(output);
                print = true;
            }

        }
        else
        {
            handleWidth(currentResult);
            handleFill(currentResult);
            handleBagExpSize(currentResult);

        }
        if (whenToPrint == ALWAYS) {
            print = true;
        } else if (whenToPrint == NEVER){
            print = false;
        }
        if (print) {
            currentResult.printCSVByTime(output);
        }

    }


    void handleWidth(ResultInformation currentResult)
    {
        int width = currentResult.getWidth();
        if (width <= firstWidth)
        {
            goodWidthCount++;
        }
        if (width < minWidth)
        {
            minWidthResult = currentResult;
            minWidth = width;
            minWidthCount = 1;
            print = true;
        }
        else if (width == minWidth)
        {
            minWidthCount++;
        }
        else if (width > maxWidth)
        {
            maxWidth = width;
        }
    }

    void handleFill(ResultInformation currentResult)
    {
        int fill = currentResult.getFill();
        if (fill <= firstFill) {
            goodFillCount++;
        }
        if (fill < minFill) {
            minFillResult = currentResult;
            minFill = fill;
            minFillCount = 1;
            print = true;
        } else if (fill == minFill) {
            minFillCount ++;
        }  else if (fill > maxFill) {
            maxFill = fill;
        }
    }

    void handleBagExpSize(ResultInformation currentResult)
    {
        long bagExpSize = currentResult.getExpBagSize();
        if (bagExpSize < minBagExpSize) {
            minBagExpSizeResult = currentResult;
            minBagExpSize = bagExpSize;
            print = true;
        } else if (bagExpSize > maxBagExpSize) {
            maxBagExpSize = bagExpSize;
        }
    }

    public static void printTableSummaryHeader(PrintWriter out)
    {
        out.println("Results,First Width,Min Width,Max Width,Best Width Time,Best Width Count,Good width Count,First Fill,Min Fill,Max Fill,Best Fill Time,Best Fill Count,Good Fill Count,First ExpBags,Min ExpBags,Max ExpBags,Best ExpBags Time");
    }

    public void printTableSummary(PrintWriter out)
    {
        StringBuilder sb = new StringBuilder();
        sb.
                append(resultsFound).append(", ").
                append(firstWidth).append(", ").
                append(minWidth).append(", ").
                append(maxWidth).append(", ").
                append(minWidthResult.getTime()).append(", ").
                append(minWidthCount).append(", ").
                append(goodWidthCount).append(", ").
                append(firstFill).append(", ").
                append(minFill).append(", ").
                append(maxFill).append(", ").
                append(minFillResult.getTime()).append(", ").
                append(minWidthCount).append(", ").
                append(goodFillCount).append(", ").
                append(firstResult.getExpBagSize()).append(", ").
                append(minBagExpSize).append(", ").
                append(maxBagExpSize).append(", ").
                append(minBagExpSizeResult.getTime());
        out.println(sb.toString());
    }



    public void printReadableSummary(PrintWriter out)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(resultsFound).append( " results found, total time").append(getTime()).append(" seconds").append(System.lineSeparator());

        if (minWidth == maxWidth)
        {
            sb.append("Width ").append(minWidth).append(", ");
        }
        else
        {
            sb.append("Width ").append(minWidth).append("-").append(maxWidth).append(", ");
        }

        if (minFill == maxFill)
        {
            sb.append("fill ").append(minFill).append(", ");
        }
        else
        {
            sb.append("fill ").append(minFill).append("-").append(maxFill).append(", ");
        }

        if (minBagExpSize == maxBagExpSize)
        {
            sb.append("exponential bags size ").append(minBagExpSize).append(".").append(System.lineSeparator());
        }
        else
        {
            sb.append("exponential bags size ").append(minBagExpSize).append("-").append(maxBagExpSize).append(".").append(System.lineSeparator());
        }

        sb.append(minWidthCount).append(" results with minimal width");
        sb.append(goodWidthCount).append(" results with width at least as good as the first found.").append(System.lineSeparator());

        if (resultsFound > 1)
        {
            sb.append("First result: ").append(firstResult).append(System.lineSeparator());
            sb.append("Lowest fill: ").append(minFillResult).append(System.lineSeparator());
            sb.append("Lowest width: ").append(minWidthResult).append(System.lineSeparator());
            sb.append("Lowest exp.b.size: ").append(minBagExpSizeResult).append(System.lineSeparator());
        }

        out.println(sb.toString());
    }

    public void printReadableSummary()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(resultsFound).append( " results found, total time").append(getTime()).append(" seconds").append(System.lineSeparator());

        if (minWidth == maxWidth)
        {
            sb.append("Width ").append(minWidth).append(", ");
        }
        else
        {
            sb.append("Width ").append(minWidth).append("-").append(maxWidth).append(", ");
        }

        if (minFill == maxFill)
        {
            sb.append("fill ").append(minFill).append(", ");
        }
        else
        {
            sb.append("fill ").append(minFill).append("-").append(maxFill).append(", ");
        }

        if (minBagExpSize == maxBagExpSize)
        {
            sb.append("exponential bags size ").append(minBagExpSize).append(".").append(System.lineSeparator());
        }
        else
        {
            sb.append("exponential bags size ").append(minBagExpSize).append("-").append(maxBagExpSize).append(".").append(System.lineSeparator());
        }

        sb.append(minWidthCount).append(" results with minimal width");
        sb.append(goodWidthCount).append(" results with width at least as good as the first found.").append(System.lineSeparator());

        if (resultsFound > 1)
        {
            sb.append("First result: ").append(firstResult).append(System.lineSeparator());
            sb.append("Lowest fill: ").append(minFillResult).append(System.lineSeparator());
            sb.append("Lowest width: ").append(minWidthResult).append(System.lineSeparator());
            sb.append("Lowest exp.b.size: ").append(minBagExpSizeResult).append(System.lineSeparator());
        }

        System.out.println(sb.toString());
    }

    public int getResultsFound() {
        return resultsFound;
    }
}
