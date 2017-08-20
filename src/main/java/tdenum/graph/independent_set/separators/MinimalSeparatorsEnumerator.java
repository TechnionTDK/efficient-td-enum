package tdenum.graph.independent_set.separators;

import tdenum.graph.data_structures.*;
import tdenum.graph.graphs.interfaces.IGraph;

/**
 * Created by dvird on 17/07/10.
 */
public class MinimalSeparatorsEnumerator
{
    IGraph graph;
    SeparatorScorer scorer;
    WeightedNodeSetQueue separatorsToExtend = new WeightedNodeSetQueue();
    NodeSetSet separatorsExtended = new NodeSetSet();


    public MinimalSeparatorsEnumerator(IGraph g, SeparatorsScoringCriterion c)
    {
        graph = g;
        scorer = new SeparatorScorer(g, c);
        for (Node v : g.getNodes())
        {
            NodeSet vAndNeighbors = graph.getNeighbors(v);
            vAndNeighbors.add(v);
            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
            {
                NodeSet potentialSeparator = graph.getNeighbors(nodeSet);
                if (potentialSeparator.size() >0)
                {
                    int score = scorer.scoreSeparator(potentialSeparator);
                    separatorsToExtend.setWeight( potentialSeparator, score);
                }
            }
        }
//        System.out.println("seperators number" + separatorsToExtend.size());
//        System.out.println("seperators " + separatorsToExtend.getKeys());
    }

    public MinimalSeparator next()
    {
        if (!hasNext())
        {
            return new MinimalSeparator();
        }
        MinimalSeparator s =  new MinimalSeparator((NodeSet) separatorsToExtend.pop());
        separatorsExtended.add(s);
        for (Node x : s)
        {
            NodeSet xNeighborsAndS = graph.getNeighbors(x);
            xNeighborsAndS.addAll(s);
            for (NodeSet nodeSet : graph.getComponents(xNeighborsAndS))
            {
                minimalSeparatorFound(graph.getNeighbors(nodeSet));
            }
        }
        return s;
    }

    public boolean hasNext()
    {
        return !separatorsToExtend.isEmpty();
    }

    public <T extends NodeSet> void minimalSeparatorFound(final T s)
    {
        if (s.size() > 0 && !separatorsExtended.contains(s))
        {
            int score = scorer.scoreSeparator(s);
            separatorsToExtend.setWeight( s, score);
        }
    }
}
