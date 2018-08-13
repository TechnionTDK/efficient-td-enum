package tdk_enum.factories.tree_decomposition_enumerator_factory;

import tdk_enum.enumerators.independent_set.IMaximalIndependentSetsEnumerator;
import tdk_enum.enumerators.tree_decomposition.ITreeDecompositionEnumerator;
import tdk_enum.enumerators.tree_decomposition.parallel.ParallelTreeDecompositionEnumerator;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.maximal_independent_sets_enumerator_factory.MaximalIndependentSetsEnumeratorFactory;
import tdk_enum.factories.minimal_triangulations_enumerator_factory.MinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.result_handler_factory.ResultHandlerFactory;

public class ParallelTreeDecompositionEnumeratorFactory implements ITreeDecompositionEnumeratorFactory {
    @Override
    public ITreeDecompositionEnumerator produce() {
        return inject(new ParallelTreeDecompositionEnumerator());
    }

    private ITreeDecompositionEnumerator inject(ParallelTreeDecompositionEnumerator enumerator) {
        enumerator.setGraph(TDKEnumFactory.getGraph());
        IMinimalTriangulationsEnumerator triangulationsEnumerator = new MinimalTriangulationsEnumeratorFactory().produce();
        triangulationsEnumerator.setResultPrinter(enumerator);
        enumerator.setResultPrinter(new ResultHandlerFactory().produce());
        enumerator.setMinimalTriangulationsEnumerator(triangulationsEnumerator);

        return enumerator;
    }
}
