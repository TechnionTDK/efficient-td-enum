package tdk_enum.factories.enumeration.result_handler_factory.minimal_separators;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.common.IO.result_handler.minimal_seperator.parallel.ParallelMinimalSperatorResultHandler;
import tdk_enum.common.configuration.TDKSeperatorsEnumConfiguration;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.enumeration.result_handler_factory.IResultHandlerFactory;

public class ParallelMinimalSeparatorsResultHandlerFactory implements IResultHandlerFactory {
    @Override
    public IResultHandler produce() {
        return inject (new ParallelMinimalSperatorResultHandler());
    }

    private IResultHandler inject(ParallelMinimalSperatorResultHandler resultHandler) {
        resultHandler.setGraph(TDKEnumFactory.getGraph());

        resultHandler.setFileNameAddition(TDKEnumFactory.getConfiguration().getFileName());

//        resultHandler.createDetailedOutput();
        resultHandler.createSummaryFile();
        resultHandler.setWhenToPrint(TDKEnumFactory.getConfiguration().getWhenToPrint());


        resultHandler.setEnumeratorType(getEnumeratorString());
        resultHandler.setThreadNumber((((TDKSeperatorsEnumConfiguration) TDKEnumFactory.getConfiguration()).getThreadNumder()));

        return  resultHandler;
    }


    private String getEnumeratorString() {
        StringBuilder sb = new StringBuilder();
        sb.append(((TDKSeperatorsEnumConfiguration) TDKEnumFactory.getConfiguration()).getParallelSeperatorsEnumeratorType());
        return sb.toString();
    }
}
