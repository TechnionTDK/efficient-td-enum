package tdk_enum.common.IO.result_handler.tree_decomposition.single_thread;

import tdk_enum.common.IO.result_handler.chordal_graph.ChordalGraphResultInformation;
import tdk_enum.common.IO.result_handler.tree_decomposition.AbstractTreeDecompositionResultHandler;
import tdk_enum.common.IO.result_handler.tree_decomposition.TreeDecompositionResultInformation;
import tdk_enum.enumerators.tree_decomposition.AbstractTreeDecompositionEnumerator;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import static tdk_enum.common.configuration.config_types.WhenToPrint.ALWAYS;
import static tdk_enum.common.configuration.config_types.WhenToPrint.NEVER;

public class SingleThreadTreeDecompositionResultHandler extends AbstractTreeDecompositionResultHandler {

    @Override
    public void newResult(ITreeDecomposition decomposition) {
        results.add(decomposition);
        TreeDecompositionResultInformation currentResult = new TreeDecompositionResultInformation(resultsFound, getTime(), graph, decomposition);
        print = false;



        if(results.size() == 1)
        {
            firstResult = minBagExpSizeResult = minFillResult = minWidthResult  = currentResult;
            firstWidth = minWidth = maxWidth = currentResult.getWidth();
            firstFill = minFill = maxFill = currentResult.getFill();
            minBagExpSize = maxBagExpSize = currentResult.getExpBagSize();
            goodWidthCount = minWidthCount = 1;
            goodFillCount = minFillCount = 1;
            if(whenToPrint != NEVER)
            {
                firstResult.printCsvHeaderByTime(detailedOutput);
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


    void handleWidth(TreeDecompositionResultInformation currentResult)
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

    void handleFill(TreeDecompositionResultInformation currentResult)
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

    void handleBagExpSize(TreeDecompositionResultInformation currentResult)
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
}
