package tdk_enum.factories.minimal_triangulations_enumerator_factory;

import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.independent_set.IMaximalIndependentSetsEnumerator;
import tdk_enum.graph.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.graph.triangulation.parallel.ParallelMinimalTriangulationsEnumerator;

public class ParallelMinimalTriangulationsEnumeratorFactory implements IMinimalTriangulationsEnumeratorFactory {
    IMinimalTriangulationsEnumerator enumerator;

    @Override
    public IMinimalTriangulationsEnumerator produce()
    {

        if(enumerator==null)
        {
            enumerator = inject(new ParallelMinimalTriangulationsEnumerator());
        }
        return  enumerator;


    }

    IMinimalTriangulationsEnumerator inject(IMinimalTriangulationsEnumerator enumerator)
    {
        enumerator.setGraph(TDEnumFactory.getGraph());
        enumerator.setSeparatorGraph(TDEnumFactory.getSeparatorGraphFactory().produce());
        IMaximalIndependentSetsEnumerator setsEnumerator = TDEnumFactory.getMaximalIndependentSetsEnumeratorFactory().produce();
        setsEnumerator.setResultPrinter(enumerator);
        enumerator.setResultPrinter(TDEnumFactory.getResultHandlerFactory().produce());
        enumerator.setSetsEnumerator(setsEnumerator);



        return enumerator;
    }
}
