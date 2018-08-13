package tdk_enum.factories.result_handler_factory;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.result_handler_factory.minimal_separators.ParallelMinimalSeparatorsResultHandlerFactory;
import tdk_enum.factories.result_handler_factory.minimal_separators.SingleThreadMinimalSeparatorsResultHandlerFactory;
import tdk_enum.factories.result_handler_factory.minimal_triangulations.ParallelMinimalTriangulationsResultHandlerFactory;
import tdk_enum.factories.result_handler_factory.minimal_triangulations.SingleThreadMinimalTriangulationsResultHandlerFactory;

public class MinimalSeperatorsResultHandlerFactory implements  IResultHandlerFactory  {
    @Override
    public IResultHandler produce() {
        switch (TDKEnumFactory.getConfiguration().getRunningMode())
        {
            case PARALLEL:
                return new ParallelMinimalSeparatorsResultHandlerFactory().produce();
            case SINGLE_THREAD:
                return new SingleThreadMinimalSeparatorsResultHandlerFactory().produce();
            default:
                return new SingleThreadMinimalSeparatorsResultHandlerFactory().produce();
        }
    }
}
