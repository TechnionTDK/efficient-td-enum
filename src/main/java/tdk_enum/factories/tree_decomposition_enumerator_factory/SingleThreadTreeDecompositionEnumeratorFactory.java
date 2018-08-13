package tdk_enum.factories.tree_decomposition_enumerator_factory;

import tdk_enum.enumerators.tree_decomposition.ITreeDecompositionEnumerator;
import tdk_enum.enumerators.tree_decomposition.single_thread.TreeDecompositionEnumerator;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.minimal_triangulations_enumerator_factory.MinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.result_handler_factory.ResultHandlerFactory;

public class SingleThreadTreeDecompositionEnumeratorFactory implements ITreeDecompositionEnumeratorFactory {
    @Override
    public ITreeDecompositionEnumerator produce() {
        return inject(new TreeDecompositionEnumerator());
    }

    private ITreeDecompositionEnumerator inject(TreeDecompositionEnumerator enumerator) {
        enumerator.setGraph(TDKEnumFactory.getGraph());
        IMinimalTriangulationsEnumerator triangulationsEnumerator = new MinimalTriangulationsEnumeratorFactory().produce();
        triangulationsEnumerator.setResultPrinter(enumerator);
        enumerator.setResultPrinter(new ResultHandlerFactory().produce());
        enumerator.setMinimalTriangulationsEnumerator(triangulationsEnumerator);

        return enumerator;
    }
}
