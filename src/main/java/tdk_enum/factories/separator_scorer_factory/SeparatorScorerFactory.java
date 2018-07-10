package tdk_enum.factories.separator_scorer_factory;

import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.separators.SeparatorsScoringCriterion;
import tdk_enum.graph.separators.scorer.ISeparatorScorer;
import tdk_enum.graph.separators.scorer.single_thread.SeparatorScorer;

import static tdk_enum.graph.separators.SeparatorsScoringCriterion.UNIFORM;

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
