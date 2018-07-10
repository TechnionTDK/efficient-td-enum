package tdenum.factories.result_handler_factory;

import tdenum.common.IO.WhenToPrint;
import tdenum.common.IO.result_handler.AbstractResultHandler;
import tdenum.common.IO.result_handler.IResultHandler;
import tdenum.common.IO.result_handler.single_thread.SingleThreadResultHandler;
import tdenum.factories.TDEnumFactory;
import tdenum.graph.separators.SeparatorsScoringCriterion;
import tdenum.graph.triangulation.TriangulationScoringCriterion;
import tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

import static tdenum.common.IO.WhenToPrint.NEVER;
import static tdenum.graph.separators.SeparatorsScoringCriterion.UNIFORM;
import static tdenum.graph.triangulation.TriangulationScoringCriterion.NONE;
import static tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.MCS_M;

public class SingleThreadResultHandlerFactory implements IResultHandlerFactory {


    IResultHandler resultHandler;

    @Override
    public IResultHandler produce() {
        if(resultHandler == null)
        {
            resultHandler =  inject(new SingleThreadResultHandler());
        }
        return resultHandler;

    }

    IResultHandler inject(IResultHandler resultHandler)
    {
        String algorithm = "mcs";
        TriangulationAlgorithm heuristic = TriangulationAlgorithm.valueOf(TDEnumFactory.getProperties().getProperty("alg", MCS_M.name()));
        TriangulationScoringCriterion trianguationsOrder = TriangulationScoringCriterion.valueOf(TDEnumFactory.getProperties().getProperty("t_order", NONE.name()));
        SeparatorsScoringCriterion separatorsOrder = SeparatorsScoringCriterion.valueOf(TDEnumFactory.getProperties().getProperty("s_order",UNIFORM.name()));

        if (heuristic!= MCS_M && trianguationsOrder!=NONE && separatorsOrder!=UNIFORM)
        {
            algorithm = new StringBuilder().append(heuristic.name()).append(".").append(trianguationsOrder.name()).
                    append(".").append(separatorsOrder.name()).toString();

        }
        resultHandler.setAlgorithm(algorithm);
        resultHandler.setGraph(TDEnumFactory.getGraph());

        resultHandler.setFileNameAddition(TDEnumFactory.getProperties().getProperty("fileNameDesc",""));

//        resultHandler.createDetailedOutput();
        resultHandler.createSummaryFile();
        resultHandler.setWhenToPrint(WhenToPrint.valueOf(TDEnumFactory.getProperties().getProperty("print", NEVER.name())));



        return  resultHandler;
    }



}
