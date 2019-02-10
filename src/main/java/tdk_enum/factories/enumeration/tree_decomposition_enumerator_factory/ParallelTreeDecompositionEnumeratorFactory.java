package tdk_enum.factories.enumeration.tree_decomposition_enumerator_factory;

import tdk_enum.enumerators.tree_decomposition.ITreeDecompositionEnumerator;
import tdk_enum.enumerators.tree_decomposition.parallel.ParallelTreeDecompositionEnumerator;
import tdk_enum.enumerators.tree_decomposition.parallel.StoringParallelNiceTreeDecompositionEnumerator;
import tdk_enum.enumerators.tree_decomposition.parallel.StoringParallelTreeDecomposistionEnumerator;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.enumeration.minimal_triangulations_enumerator_factory.MinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.enumeration.result_handler_factory.ResultHandlerFactory;

public class ParallelTreeDecompositionEnumeratorFactory implements ITreeDecompositionEnumeratorFactory {
    @Override
    public ITreeDecompositionEnumerator produce() {

        switch (TDKEnumFactory.getConfiguration().getEnumerationType()){
            case SAVE_PROPER_TD:{
                return inject(new StoringParallelTreeDecomposistionEnumerator());
            }
            case SAVE_NICE_TD:{
                return inject(new StoringParallelNiceTreeDecompositionEnumerator());
            }
        }

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
