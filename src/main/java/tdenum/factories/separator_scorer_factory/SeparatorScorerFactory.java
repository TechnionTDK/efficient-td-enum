package tdenum.factories.separator_scorer_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.separators.SeparatorsScoringCriterion;
import tdenum.graph.separators.scorer.ISeparatorScorer;
import tdenum.graph.separators.scorer.single_thread.SeparatorScorer;

import static tdenum.graph.separators.SeparatorsScoringCriterion.UNIFORM;

public class SeparatorScorerFactory implements ISeparatorScorerFactory {
    @Override
    public ISeparatorScorer produce() {
        return inject(new SeparatorScorer());
    }

    ISeparatorScorer inject(ISeparatorScorer scorer)
    {
        scorer.setGraph(TDEnumFactory.getGraph());
        scorer.setCriterion(SeparatorsScoringCriterion.valueOf(TDEnumFactory.getProperties().getProperty("s_order",UNIFORM.name())));
        return scorer;
    }
}
