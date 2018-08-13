package tdk_enum.factories.result_handler_factory;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.result_handler_factory.minimal_triangulations.ParallelMinimalTriangulationsResultHandlerFactory;
import tdk_enum.factories.result_handler_factory.minimal_triangulations.SingleThreadMinimalTriangulationsResultHandlerFactory;
import tdk_enum.factories.result_handler_factory.tree_decomposition.ParallelTreeDecompositionResultHandlerFactory;
import tdk_enum.factories.result_handler_factory.tree_decomposition.SingleThreadTreeDecompositionResultHandlerFactory;

public class TreeDecompositionResultHandlerFactory implements IResultHandlerFactory {
    @Override
    public IResultHandler produce() {
        switch (TDKEnumFactory.getConfiguration().getRunningMode())
        {
            case PARALLEL:
                return new ParallelTreeDecompositionResultHandlerFactory().produce();
            case SINGLE_THREAD:
                return new SingleThreadTreeDecompositionResultHandlerFactory().produce();
            default:
                return new SingleThreadTreeDecompositionResultHandlerFactory().produce();
        }
    }
}
