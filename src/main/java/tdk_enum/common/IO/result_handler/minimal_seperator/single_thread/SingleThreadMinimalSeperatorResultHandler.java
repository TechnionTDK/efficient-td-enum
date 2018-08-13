package tdk_enum.common.IO.result_handler.minimal_seperator.single_thread;

import tdk_enum.common.IO.result_handler.minimal_seperator.AbstractMinimalSeperatorResultHandler;
import tdk_enum.common.IO.result_handler.minimal_seperator.MinimalSeperatorResultInformation;
import tdk_enum.graph.data_structures.MinimalSeparator;

import static tdk_enum.common.IO.CSVOperations.dataToCSV;
import static tdk_enum.common.configuration.config_types.WhenToPrint.ALWAYS;
import static tdk_enum.common.configuration.config_types.WhenToPrint.NEVER;

public class SingleThreadMinimalSeperatorResultHandler extends AbstractMinimalSeperatorResultHandler {

    @Override
    public void newResult(MinimalSeparator minimalSeparator) {
        ++resultsFound;
        MinimalSeperatorResultInformation currentResult = new MinimalSeperatorResultInformation(resultsFound, getTime());
        print = false;



        if(resultsFound == 1)
        {
            firstResult  = currentResult;

            if(whenToPrint != NEVER)
            {
                firstResult.printCsvHeaderByTime(detailedOutput);
                print = true;
            }

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


    @Override
    public void printSummaryTable()
    {
        separators = resultsFound;
        super.printSummaryTable();

    }



}
