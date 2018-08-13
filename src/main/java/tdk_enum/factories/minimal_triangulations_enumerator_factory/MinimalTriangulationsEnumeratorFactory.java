package tdk_enum.factories.minimal_triangulations_enumerator_factory;

import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.factories.TDKEnumFactory;

public class MinimalTriangulationsEnumeratorFactory implements IMinimalTriangulationsEnumeratorFactory {
    @Override
    public IMinimalTriangulationsEnumerator produce() {

        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        switch (configuration.getRunningMode())
        {
            case SINGLE_THREAD:
                return new SingleThreadMinimalTriangulationsEnumeratorFactory().produce();
            case PARALLEL:
                return new ParallelMinimalTriangulationsEnumeratorFactory().produce();
            default:
                return new SingleThreadMinimalTriangulationsEnumeratorFactory().produce();
        }
    }
}
