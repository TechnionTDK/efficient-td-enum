package tdenum.graph.separators;

import tdenum.graph.data_structures.*;
import tdenum.graph.graphs.Graph;

/**
 * Created by dvird on 17/07/10.
 */
public class MinimalSeparatorEnumerator
{
    Graph graph;
    SeparatorScorer scorer;
    WeightedNodeSetQueue separatorsToExtend = new WeightedNodeSetQueue();
    NodeSetSet separatorsExtended = new NodeSetSet();


    public MinimalSeparatorEnumerator(Graph g, SeparatorsScoringCriterion c)
    {
        graph = g;
        scorer = new SeparatorScorer(g, c);
        for (Node v : g.getNodes())
        {
            NodeSet vAndNeighbors = graph.getNeighbors(v);
            vAndNeighbors.add(v);
            for (NodeSet nodeSet : graph.getComponents(vAndNeighbors))
            {
                NodeSet potentialSeperator = graph.getNeighbors(nodeSet);
                if (potentialSeperator.size() >0)
                {
                    int score = scorer.scoreSeparator(potentialSeperator);
                    separatorsToExtend.insert(potentialSeperator, score);
                }
            }
        }
    }

    public MinimalSeparator next()
    {
        if (!hasNext())
        {
            return new MinimalSeparator();
        }
        MinimalSeparator s = separatorsToExtend.pop();
        separatorsExtended.add(s);
        for (Node x : s)
        {
            NodeSet xNeighbotsAndS = graph.getNeighbors(x);
            xNeighbotsAndS.addAll(s);
            for (NodeSet nodeSet : graph.getComponents(xNeighbotsAndS))
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
            separatorsToExtend.insert(s, score);
        }
    }
}
