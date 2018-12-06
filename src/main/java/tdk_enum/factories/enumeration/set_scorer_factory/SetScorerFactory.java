package tdk_enum.factories.enumeration.set_scorer_factory;

import tdk_enum.common.Utils;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.enumerators.independent_set.scoring.IIndependentSetScorer;
import tdk_enum.enumerators.independent_set.scoring.single_thread.IndSetScorerByTriangulation;
import tdk_enum.common.configuration.config_types.TriangulationScoringCriterion;


import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.NONE;



public class SetScorerFactory implements ISetScorerFactory
{

    @Override
    public IIndependentSetScorer produce() {
        return inject(new IndSetScorerByTriangulation());
    }

    IIndependentSetScorer inject(IIndependentSetScorer scorer)
    {
        TriangulationScoringCriterion scoringCriterion = (TriangulationScoringCriterion)
                Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "triangulationScoringCriterion", NONE);
        System.out.println("Producing Independent Sets scorer. Criterion: " + scoringCriterion);
        scorer.setGraph(TDKEnumFactory.getGraph());
        scorer.setCriterion(scoringCriterion);
//        scorer.setCriterion(TriangulationScoringCriterion.valueOf(TDKEnumFactory.getProperties().getProperty("t_order", NONE.name())));
        return scorer;
    }
}
