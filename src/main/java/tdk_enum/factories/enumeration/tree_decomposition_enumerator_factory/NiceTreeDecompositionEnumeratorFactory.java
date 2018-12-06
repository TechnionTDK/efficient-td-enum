package tdk_enum.factories.enumeration.tree_decomposition_enumerator_factory;

import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.enumerators.tree_decomposition.ITreeDecompositionEnumerator;
import tdk_enum.factories.TDKEnumFactory;

public class NiceTreeDecompositionEnumeratorFactory implements ITreeDecompositionEnumeratorFactory{
    @Override
    public ITreeDecompositionEnumerator produce() {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        switch (configuration.getRunningMode())
        {
            case SINGLE_THREAD:
                return new SingleThreadNiceTreeDecompositionEnumeratorFactory().produce();
            case PARALLEL:
                return new ParallelNiceTreeDecompositionEnumeratorFactory().produce();
            default:
                return new SingleThreadNiceTreeDecompositionEnumeratorFactory().produce();
        }
    }
}
