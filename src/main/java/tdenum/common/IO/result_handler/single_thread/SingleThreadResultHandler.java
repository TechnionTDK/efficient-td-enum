package tdenum.common.IO.result_handler.single_thread;

import tdenum.common.IO.result_handler.AbstractResultHandler;
import tdenum.common.IO.result_handler.ResultInformation;
import tdenum.graph.graphs.chordal_graph.IChordalGraph;
import tdenum.graph.graphs.chordal_graph.single_thread.ChordalGraph;

import static tdenum.common.IO.WhenToPrint.ALWAYS;
import static tdenum.common.IO.WhenToPrint.NEVER;

public class SingleThreadResultHandler extends AbstractResultHandler {


    @Override
    public void newResult(IChordalGraph triangulation) {
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
                ResultInformation.printCsvHeaderByTime(detailedOutput);
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
            currentResult.printCSVByTime(detailedOutput);
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

    @Override
    public void print(IChordalGraph result) {
        newResult(result);

    }
}
