package tdk_enum.enumerators.separators;

import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.common.AbstractPolynomialDelayEnumerator;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.enumerators.separators.scorer.ISeparatorScorer;

import java.util.function.Function;

public abstract class AbstractMinimalSeparatorsEnumerator extends AbstractPolynomialDelayEnumerator<Node, MinimalSeparator ,IGraph> implements IMinimalSeparatorsEnumerator  {

//    protected IGraph graph;
    protected ISeparatorScorer scorer;
//    protected IWeightedQueue separatorsToExtend;
//    protected Set<NodeSet> separatorsExtended ;
//
//
//    @Override
//    public void setGraph(IGraph graph) {
//        this.graph = graph;
//    }
//
    @Override
    public void setScorer(ISeparatorScorer scorer) {
        this.scorer = scorer;
    }

//    @Override
//    public void setSeparatorsToExtend(IWeightedQueue separatorsToExtend) {
//        this.separatorsToExtend = separatorsToExtend;
//    }
//
//    @Override
//    public void setSeparatorsExtended(Set<NodeSet> separatorsExtended) {
//        this.separatorsExtended = separatorsExtended;
//    }

    @Override
    public int numberOfSeparators()
    {
        return P.size();
    }



    @Override
    protected void iteratingResultsPhase() {

    }



    @Override
    protected boolean stepByStepIteratingResultsPhase() {
        return false;
    }


    @Override
    public  void setPurpose(EnumerationPurpose purpose)
    {
        this.purpose =purpose;
        switch (purpose)
        {
            case STANDALONE:
                finishConditionFunction = new Function<Void, Boolean>() {
                    @Override
                    public Boolean apply(Void aVoid) {
                        return Thread.currentThread().isInterrupted();
                    }
                };
                break;
            case BENCHMARK_COMPARE:
                finishConditionFunction = new Function<Void, Boolean>() {
                    @Override
                    public Boolean apply(Void aVoid) {
                        if(capacity <=  P.size())
                        {
                            return true;

                        }
                        return false ;
                    }
                };
                break;
        }
    }


//}
}
