package tdk_enum.factories.maximal_independent_sets_enumerator_factory;

import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.enumerators.independent_set.IMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.independent_set.parallel.ParallelMaximalIndependentSetsEnumerator;
import tdk_enum.factories.TDKEnumFactory;

public class MaximalIndependentSetsEnumeratorFactory implements IMaximalIndependentSetsEnumeratorFactory {
    @Override
    public IMaximalIndependentSetsEnumerator produce() {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        switch (configuration.getRunningMode())
        {
            case PARALLEL:
                return new ParallelMaximalIndependentSetsEnumeratorFactory().produce();
            case SINGLE_THREAD:
                return new SingleThreadMaximalIndependentSetsEnumeratorFactory().produce();
            default:
                return new SingleThreadMaximalIndependentSetsEnumeratorFactory().produce();
        }
    }
}
