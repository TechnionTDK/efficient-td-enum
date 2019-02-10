package tdk_enum.factories.enumeration.enumerator_factory;

import tdk_enum.common.Utils;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.enumeration.minimal_separators_enumerator_factory.MinimalSeperatorsEnumeratorFactory;
import tdk_enum.factories.enumeration.minimal_triangulations_enumerator_factory.MinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.enumeration.result_handler_factory.ResultHandlerFactory;
import tdk_enum.factories.enumeration.tree_decomposition_enumerator_factory.NiceTreeDecompositionEnumeratorFactory;
import tdk_enum.factories.enumeration.tree_decomposition_enumerator_factory.TreeDecompositionEnumeratorFactory;

import static tdk_enum.common.configuration.config_types.EnumerationPurpose.BENCHMARK_COMPARE;
import static tdk_enum.common.configuration.config_types.EnumerationPurpose.STANDALONE;

public class EnumeratorFactory implements  IEnumeratorFactory {
    @Override
    public IEnumerator produce() {

        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();

        switch (configuration.getEnumerationType())
        {
            case NICE_TD:
                return produceNiceTreeDecompositionEnumerator();
            case PROPER_TD:
                return produceTreeDecompositionEnumerator();
            case SEPARATORS:
                return produceMinimalSeparatorsEnumerator();
            case MINIMAL_TRIANGULATIONS: case SAVE_NICE_TD:
                return produceMinimalTriangluationsEnumerator();

            default:
                return null;
        }


    }

    private IEnumerator produceNiceTreeDecompositionEnumerator() {
        IEnumerator enumerator = new NiceTreeDecompositionEnumeratorFactory().produce();
        return inject(enumerator);
    }

    private IEnumerator produceTreeDecompositionEnumerator() {
        IEnumerator enumerator = new TreeDecompositionEnumeratorFactory().produce();
        return inject(enumerator);
    }

    private IEnumerator produceMinimalTriangluationsEnumerator() {
        IEnumerator enumerator = new MinimalTriangulationsEnumeratorFactory().produce();

        return inject(enumerator);
    }

    private IEnumerator produceMinimalSeparatorsEnumerator() {
        IEnumerator enumerator = new MinimalSeperatorsEnumeratorFactory().produce();

        return inject(enumerator);
    }

    private IEnumerator inject(IEnumerator enumerator)
    {
        enumerator.setResultPrinter(new ResultHandlerFactory().produce());
        EnumerationPurpose enumerationPropose = (EnumerationPurpose) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "enumerationPurpose", STANDALONE);
        enumerator.setPurpose(enumerationPropose);
        if(enumerationPropose.equals(BENCHMARK_COMPARE))
        {
            enumerator.setCapacity(TDKEnumFactory.getBenchMarkResults());
        }
        return enumerator;
    }




}
