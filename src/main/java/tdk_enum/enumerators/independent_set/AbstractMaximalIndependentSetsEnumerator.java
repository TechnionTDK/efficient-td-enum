package tdk_enum.enumerators.independent_set;

import tdk_enum.common.cache.Cache;
import tdk_enum.common.cache.ICache;
import tdk_enum.common.configuration.config_types.EnumerationPurpose;
import tdk_enum.enumerators.common.AbstractIncrementalPolynomialDelayEnumerator;
import tdk_enum.enumerators.common.DefaultBuilder;
import tdk_enum.graph.graphs.succinct_graphs.ISuccinctGraphRepresentation;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public abstract class AbstractMaximalIndependentSetsEnumerator<T> extends AbstractIncrementalPolynomialDelayEnumerator<T, Set<T>, ISuccinctGraphRepresentation<T>> implements IMaximalIndependentSetsEnumerator<T>{

//    protected ISuccinctGraphRepresentation<T> graph;
//    protected IIndependentSetExtender extender;
//    protected IIndependentSetScorer scorer;



//    protected IWeightedQueue<Set<T>> Q = new WeightedQueue<>();


//    protected Set<T> V = new HashSet<>();
//    protected Set<Set<T>> P = new HashSet<>();
//    protected Set<Set<T>> setsNotExtended = new HashSet<>();


//    protected boolean nextSetReady;
//    protected Set<T> nextIndependentSet;
//
//    protected Set<T> currentSet;
//    protected T currentNode;


    public AbstractMaximalIndependentSetsEnumerator()
    {
        super();
        defaultBuilder = new DefaultBuilder(HashSet::new);
    }


    protected ICache jvCache = new Cache();
    @Override
    public void setCache(ICache cache) {
        this.jvCache = cache;
    }


    @Override
    protected void changeVIfNeeded() {

    }

//    protected IResultPrinter<Set<T>> resultPrinter = new IResultPrinter<Set<T>>() {
//        @Override
//        public void print(Set<T> result) {
//
//        }
//    };


//    @Override
//    public void setGraph(ISuccinctGraphRepresentation<T> graph) {
//        this.graph = graph;
//    }

//    @Override
//    public void setV(Set<T> v) {
//        V = v;
//    }
//
//    @Override
//    public void setP(Set<Set<T>> p) {
//        P = p;
//    }

//    @Override
//    public void setSetsNotExtended(Set<Set<T>> setsNotExtended) {
//        this.setsNotExtended = setsNotExtended;
//    }
//
//
//    @Override
//    public void setExtender(IIndependentSetExtender extender) {
//        this.extender = extender;
//    }
//
//    @Override
//    public void setScorer(IIndependentSetScorer scorer) {
//        this.scorer = scorer;
//    }
//
//
//    @Override
//    public void setQ(IWeightedQueue<Set<T>> q) {
//        this.Q = q;
//    }



//    protected abstract boolean finishCondition();





//    protected boolean newSetFound(final Set<T> generatedSet)
//    {
//
//
//        if (!P.contains(generatedSet))
//        {
//            if(setsNotExtended.add(generatedSet))
//            {
//                Q.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
//
//                nextIndependentSet = generatedSet;
//                nextSetReady = true;
//                return true;
//            }
//
//            return false;
//        }
//
//        return false;
//    }

//    public IResultPrinter<Set<T>> getResultPrinter() {
//        return resultPrinter;
//    }

//    @Override
//    public void setResultPrinter(IResultPrinter<Set<T>> resultPrinter) {
//        this.resultPrinter = resultPrinter;
//    }


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
                        if(capacity <= Q.size() + P.size())
                        {
                            return true;

                        }
                        return false ;
                    }
                };
                break;
        }
    }




}
