package tdk_enum.factories.enumeration.result_handler_factory;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.enumeration.result_handler_factory.minimal_triangulations.ParallelMinimalTriangulationsResultHandlerFactory;
import tdk_enum.factories.enumeration.result_handler_factory.minimal_triangulations.SingleThreadMinimalTriangulationsResultHandlerFactory;

public class MinimalTriangulationResultHandlerFactory implements IResultHandlerFactory {
    @Override
    public IResultHandler produce() {

        switch (TDKEnumFactory.getConfiguration().getRunningMode())
        {
            case PARALLEL:
                return new ParallelMinimalTriangulationsResultHandlerFactory().produce();
            case SINGLE_THREAD:
                return new SingleThreadMinimalTriangulationsResultHandlerFactory().produce();
            default:
                return new SingleThreadMinimalTriangulationsResultHandlerFactory().produce();
        }
    }
}
