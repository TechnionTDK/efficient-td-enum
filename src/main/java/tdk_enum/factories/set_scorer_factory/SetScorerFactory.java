package tdk_enum.factories.set_scorer_factory;

import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.independent_set.scoring.IIndependentSetScorer;
import tdk_enum.graph.independent_set.scoring.single_thread.IndSetScorerByTriangulation;
import tdk_enum.graph.triangulation.TriangulationScoringCriterion;


import static tdk_enum.graph.triangulation.TriangulationScoringCriterion.NONE;



public class SetScorerFactory implements ISetScorerFactory
{

    @Override
    public IIndependentSetScorer produce() {
        return inject(new IndSetScorerByTriangulation());
    }

    IIndependentSetScorer inject(IIndependentSetScorer scorer)
    {
        scorer.setGraph(TDEnumFactory.getGraph());
        scorer.setCriterion(TriangulationScoringCriterion.valueOf(TDEnumFactory.getProperties().getProperty("t_order", NONE.name())));
        return scorer;
    }
}
