package tdk_enum.factories.minimal_triangulations_enumerator_factory;

import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.enumerators.independent_set.IMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.enumerators.triangulation.single_thread.MinimalTriangulationsEnumerator;
import tdk_enum.factories.maximal_independent_sets_enumerator_factory.MaximalIndependentSetsEnumeratorFactory;
import tdk_enum.factories.result_handler_factory.ResultHandlerFactory;

public class SingleThreadMinimalTriangulationsEnumeratorFactory implements IMinimalTriangulationsEnumeratorFactory {



    @Override
    public IMinimalTriangulationsEnumerator produce()
    {
        return inject(new MinimalTriangulationsEnumerator());



    }

    IMinimalTriangulationsEnumerator inject(IMinimalTriangulationsEnumerator enumerator)
    {
        enumerator.setGraph(TDKEnumFactory.getGraph());
     //   enumerator.setSeparatorGraph(TDKEnumFactory.getSeparatorGraphFactory().produce());
        IMaximalIndependentSetsEnumerator setsEnumerator = new MaximalIndependentSetsEnumeratorFactory().produce();
        setsEnumerator.setResultPrinter(enumerator);
        enumerator.setResultPrinter(new ResultHandlerFactory().produce());
        enumerator.setSetsEnumerator(setsEnumerator);



        return enumerator;
    }
}
