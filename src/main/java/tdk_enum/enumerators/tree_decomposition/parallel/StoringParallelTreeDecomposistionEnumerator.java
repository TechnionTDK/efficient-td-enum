package tdk_enum.enumerators.tree_decomposition.parallel;

import tdk_enum.graph.converters.Converter;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class StoringParallelTreeDecomposistionEnumerator extends ParallelTreeDecompositionEnumerator {

    Set<ITreeDecomposition> decompositions = ConcurrentHashMap.newKeySet();

    @Override
    public Set<ITreeDecomposition> getDecompositions() {
        return decompositions;
    }

    @Override
    public void print(IChordalGraph result) {
        ITreeDecomposition td = Converter.chordalGraphToProperTreeDecomposition(result);
        decompositions.add(td);
        resultPrinter.print(td);
    }
}
