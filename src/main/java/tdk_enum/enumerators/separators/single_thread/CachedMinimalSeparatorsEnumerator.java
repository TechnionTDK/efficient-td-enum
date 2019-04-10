package tdk_enum.enumerators.separators.single_thread;

import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.data_structures.NodeSet;

import java.util.HashSet;
import java.util.Set;

public class CachedMinimalSeparatorsEnumerator extends  MinimalSeparatorsEnumerator {

    public Set<NodeSet> getComponentsCache() {
        return componentsCache;
    }

    public void setComponentsCache(Set<NodeSet> componentsCache) {
        this.componentsCache = componentsCache;
    }

    //    Set<Set<Node>> intermediateCache = new HashSet<>();
    protected Set<NodeSet> componentsCache = new HashSet<>();

//    int cacheCounter = 0;
//    int cacheHitCounter = 0;


    @Override
    protected void tryGenerateNewResult(Node node, MinimalSeparator result)
    {
//        if (result.contains(node))
//        {
//            return;
//        }
        Set<Node> xNeighborsAndS = graph.getNeighbors(node);
        xNeighborsAndS.addAll(result);
//        cacheCounter++;
//        if (intermediateCache.contains(xNeighborsAndS)){
//            cacheHitCounter++;
//
//            return;
//        }
//        intermediateCache.add(xNeighborsAndS);
        for (NodeSet nodeSet : graph.getComponents(xNeighborsAndS))
        {


            if(componentsCache.contains(nodeSet)){

                continue;
            }
            componentsCache.add(nodeSet);
            newResultFound(new MinimalSeparator(graph.getNeighbors(nodeSet)));

        }
    }

//    @Override
//    public void executeAlgorithm(){
//
//        super.executeAlgorithm();
//        System.out.println("cache hit ratio: " + ((double) cacheHitCounter)/cacheCounter);
//
//    }
}
