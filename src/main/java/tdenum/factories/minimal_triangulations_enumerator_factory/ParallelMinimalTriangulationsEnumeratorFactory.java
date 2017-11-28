package tdenum.factories.minimal_triangulations_enumerator_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.independent_set.IMaximalIndependentSetsEnumerator;
import tdenum.graph.triangulation.IMinimalTriangulationsEnumerator;
import tdenum.graph.triangulation.parallel.ParallelMinimalTriangulationsEnumerator;
import tdenum.graph.triangulation.single_thread.MinimalTriangulationsEnumerator;

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
