package tdk_enum.factories.separator_scorer_factory;

import tdk_enum.common.Utils;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion;
import tdk_enum.enumerators.separators.scorer.ISeparatorScorer;
import tdk_enum.enumerators.separators.scorer.single_thread.SeparatorScorer;

import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;

public class SeparatorScorerFactory implements ISeparatorScorerFactory {
    @Override
    public ISeparatorScorer produce() {
        return inject(new SeparatorScorer());
    }

    ISeparatorScorer inject(ISeparatorScorer scorer)
    {
        SeparatorsScoringCriterion scoringCriterion = (SeparatorsScoringCriterion) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "separatorsScoringCriterion", UNIFORM );
        System.out.println("Producing separator scorer. Scoring criterion: " + scoringCriterion);
        scorer.setGraph(TDKEnumFactory.getGraph());

        scorer.setCriterion(scoringCriterion );
       // scorer.setCriterion(SeparatorsScoringCriterion.valueOf(TDKEnumFactory.getConfiguration()..getProperty("s_order",UNIFORM.name())));
        return scorer;
    }
}
