package tdk_enum.enumerators.tree_decomposition;

import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.common.AbstractConverterEnumerator;
import tdk_enum.enumerators.triangulation.IMinimalTriangulationsEnumerator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;

import java.util.Set;

public abstract class AbstractTreeDecompositionEnumerator extends AbstractConverterEnumerator<Node,ITreeDecomposition,IGraph> implements ITreeDecompositionEnumerator {

    protected IMinimalTriangulationsEnumerator minimalTriangulationsEnumerator;

    public IMinimalTriangulationsEnumerator getMinimalTriangulationsEnumerator() {
        return minimalTriangulationsEnumerator;
    }

    @Override
    public void setMinimalTriangulationsEnumerator(IMinimalTriangulationsEnumerator minimalTriangulationsEnumerator) {
        this.minimalTriangulationsEnumerator = minimalTriangulationsEnumerator;
    }

    @Override
    public int getNumberOfMinimalSeperatorsGenerated()
    {
        return minimalTriangulationsEnumerator.getNumberOfMinimalSeperatorsGenerated();
    }


    @Override
    public Set<ITreeDecomposition> getDecompositions(){
        return null;
    }





}
