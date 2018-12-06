package tdk_enum.factories.enumeration.result_handler_factory;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.enumeration.result_handler_factory.tree_decomposition.ParallelNiceTreeDecompositionResultHandlerFactory;
import tdk_enum.factories.enumeration.result_handler_factory.tree_decomposition.SingleThreadNiceTreeDecompositionResultHandlerFactory;

public class NiceTreeDecompositionResultHandlerFactory implements IResultHandlerFactory {
    @Override
    public IResultHandler produce() {
        switch (TDKEnumFactory.getConfiguration().getRunningMode()) {
            case PARALLEL:
                return new ParallelNiceTreeDecompositionResultHandlerFactory().produce();
            case SINGLE_THREAD:
                return new SingleThreadNiceTreeDecompositionResultHandlerFactory().produce();
            default:
                return new SingleThreadNiceTreeDecompositionResultHandlerFactory().produce();
        }
    }
}
