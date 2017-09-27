package tdenum.factories.set_scorer_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.independent_set.scoring.IIndependentSetScorer;
import tdenum.graph.independent_set.scoring.single_thread.IndSetScorerByTriangulation;
import tdenum.graph.triangulation.TriangulationScoringCriterion;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static tdenum.graph.triangulation.TriangulationScoringCriterion.NONE;



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
