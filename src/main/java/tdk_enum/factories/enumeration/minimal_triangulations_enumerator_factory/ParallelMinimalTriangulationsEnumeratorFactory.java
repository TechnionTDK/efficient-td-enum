package tdk_enum.factories.enumeration.minimal_triangulations_enumerator_factory;

import tdk_enum.common.configuration.TDKChordalGraphEnumConfiguration;
import tdk_enum.enumerators.triangulation.parallel.StoringParallelMinimalTriangulationsEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.enumerators.independent_set.IMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.enumerators.triangulation.parallel.ParallelMinimalTriangulationsEnumerator;
import tdk_enum.factories.enumeration.maximal_independent_sets_enumerator_factory.MaximalIndependentSetsEnumeratorFactory;
import tdk_enum.factories.enumeration.result_handler_factory.ResultHandlerFactory;

public class ParallelMinimalTriangulationsEnumeratorFactory implements IMinimalTriangulationsEnumeratorFactory {


    @Override
    public IMinimalTriangulationsEnumerator produce()
    {
        TDKChordalGraphEnumConfiguration configuration = (TDKChordalGraphEnumConfiguration) TDKEnumFactory.getConfiguration();
        switch (configuration.getEnumerationType())
        {
            case SAVE_MINIMAL_TRIANGULATIONS:
                return (inject (new StoringParallelMinimalTriangulationsEnumerator()));
                default:
                    return inject(new ParallelMinimalTriangulationsEnumerator());
        }




    }

    IMinimalTriangulationsEnumerator inject(IMinimalTriangulationsEnumerator enumerator)
    {
        enumerator.setGraph(TDKEnumFactory.getGraph());
      //  enumerator.setSeparatorGraph(TDKEnumFactory.getSeparatorGraphFactory().produce());
        IMaximalIndependentSetsEnumerator setsEnumerator = new MaximalIndependentSetsEnumeratorFactory().produce();
        setsEnumerator.setResultPrinter(enumerator);
        enumerator.setResultPrinter(new ResultHandlerFactory().produce());
        enumerator.setSetsEnumerator(setsEnumerator);
        return enumerator;
    }
}
