package tdenum.factories.minimal_triangulations_enumerator_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.separators.IMinimalSeparatorsEnumerator;
import tdenum.graph.triangulation.IMinimalTriangulationsEnumerator;
import tdenum.graph.triangulation.single_thread.MinimalTriangulationsEnumerator;

public class SingleThreadMinimalTriangulationsEnumeratorFactory implements IMinimalTriangulationsEnumeratorFactory {
    @Override
    public IMinimalTriangulationsEnumerator produce()
    {

        return  inject(new MinimalTriangulationsEnumerator());


    }

    IMinimalTriangulationsEnumerator inject(IMinimalTriangulationsEnumerator enumerator)
    {
        enumerator.setGraph(TDEnumFactory.getGraph());
        enumerator.setSeparatorGraph(TDEnumFactory.getSeparatorGraphFactory().produce());
        enumerator.setSetsEnumerator(TDEnumFactory.getMaximalIndependentSetsEnumeratorFactory().produce());
        return enumerator;
    }
}
