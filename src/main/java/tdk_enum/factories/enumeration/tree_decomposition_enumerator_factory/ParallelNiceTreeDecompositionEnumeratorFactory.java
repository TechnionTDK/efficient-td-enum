package tdk_enum.factories.enumeration.tree_decomposition_enumerator_factory;

import tdk_enum.enumerators.tree_decomposition.INiceTreeDecompositionEnumerator;
import tdk_enum.enumerators.tree_decomposition.parallel.ParallelNiceTreeDecompositionEnumerator;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.enumeration.minimal_triangulations_enumerator_factory.MinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.enumeration.result_handler_factory.ResultHandlerFactory;

public class ParallelNiceTreeDecompositionEnumeratorFactory implements ITreeDecompositionEnumeratorFactory {
    @Override
    public INiceTreeDecompositionEnumerator produce() {
        return inject(new ParallelNiceTreeDecompositionEnumerator());
    }

    private INiceTreeDecompositionEnumerator inject(INiceTreeDecompositionEnumerator enumerator) {
        enumerator.setGraph(TDKEnumFactory.getGraph());
        IMinimalTriangulationsEnumerator triangulationsEnumerator = new MinimalTriangulationsEnumeratorFactory().produce();
        triangulationsEnumerator.setResultPrinter(enumerator);
        enumerator.setResultPrinter(new ResultHandlerFactory().produce());
        enumerator.setMinimalTriangulationsEnumerator(triangulationsEnumerator);

        return enumerator;
    }
}
