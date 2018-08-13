package tdk_enum.factories.tree_decomposition_enumerator_factory;

import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.enumerators.tree_decomposition.ITreeDecompositionEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.minimal_triangulations_enumerator_factory.ParallelMinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.minimal_triangulations_enumerator_factory.SingleThreadMinimalTriangulationsEnumeratorFactory;

public class TreeDecompositionEnumeratorFactory implements ITreeDecompositionEnumeratorFactory {
    @Override
    public ITreeDecompositionEnumerator produce() {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        switch (configuration.getRunningMode())
        {
            case SINGLE_THREAD:
                return new SingleThreadTreeDecompositionEnumeratorFactory().produce();
            case PARALLEL:
                return new ParallelTreeDecompositionEnumeratorFactory().produce();
            default:
                return new SingleThreadTreeDecompositionEnumeratorFactory().produce();
        }
    }
}
