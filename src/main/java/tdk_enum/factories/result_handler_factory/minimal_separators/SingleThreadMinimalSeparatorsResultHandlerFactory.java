package tdk_enum.factories.result_handler_factory.minimal_separators;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.common.IO.result_handler.minimal_seperator.single_thread.SingleThreadMinimalSeperatorResultHandler;
import tdk_enum.common.configuration.TDKChordalGraphEnumConfiguration;
import tdk_enum.common.configuration.TDKSeperatorsEnumConfiguration;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.result_handler_factory.IResultHandlerFactory;

public class SingleThreadMinimalSeparatorsResultHandlerFactory implements IResultHandlerFactory {
    @Override
    public IResultHandler produce() {
        return inject(new SingleThreadMinimalSeperatorResultHandler());
    }

    private IResultHandler inject(SingleThreadMinimalSeperatorResultHandler resultHandler) {
        resultHandler.setGraph(TDKEnumFactory.getGraph());

        resultHandler.setFileNameAddition(TDKEnumFactory.getConfiguration().getFileName());

//        resultHandler.createDetailedOutput();
        resultHandler.createSummaryFile();
        resultHandler.setWhenToPrint(TDKEnumFactory.getConfiguration().getWhenToPrint());


        resultHandler.setEnumeratorType(getEnumeratorString());


        return  resultHandler;
    }

    private String getEnumeratorString() {
        StringBuilder sb = new StringBuilder();
        sb.append(((TDKSeperatorsEnumConfiguration) TDKEnumFactory.getConfiguration()).getSingleThreadSeperatorsEnumeratorType());
        return sb.toString();
    }

}
