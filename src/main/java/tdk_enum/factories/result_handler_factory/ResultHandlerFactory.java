package tdk_enum.factories.result_handler_factory;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.factories.TDKEnumFactory;

public class ResultHandlerFactory implements  IResultHandlerFactory {
    @Override
    public IResultHandler produce() {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        switch (configuration.getEnumerationType())
        {
            case MINIMAL_TRIANGULATIONS:
                return inject( new MinimalTriangulationResultHandlerFactory().produce());
            case SEPARATORS:
                return inject( new MinimalSeperatorsResultHandlerFactory().produce());
            case PROPER_TD:
                return inject( new TreeDecompositionResultHandlerFactory().produce());
            case NICE_TD:
                return null;
            default:
                return null;
        }
    }

    IResultHandler inject(IResultHandler resultHandler)
    {
        resultHandler.setType(TDKEnumFactory.getGraphType());
        resultHandler.setField(TDKEnumFactory.getGraphField());
        resultHandler.setGraphName(TDKEnumFactory.getGraphName());
        resultHandler.setNodes(TDKEnumFactory.getGraph().getNumberOfNodes());
        resultHandler.setEdges(TDKEnumFactory.getGraph().getNumberOfEdges());
        resultHandler.setEnumerationType(TDKEnumFactory.getConfiguration().getEnumerationType());
        resultHandler.setRunningMode(TDKEnumFactory.getConfiguration().getRunningMode());

        return resultHandler;
    }


}
