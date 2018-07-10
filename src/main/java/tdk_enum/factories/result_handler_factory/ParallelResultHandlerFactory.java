package tdk_enum.factories.result_handler_factory;

import tdk_enum.common.IO.WhenToPrint;
import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.common.IO.result_handler.parallel.ParallelResultHandler;
import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.separators.SeparatorsScoringCriterion;
import tdk_enum.graph.triangulation.TriangulationScoringCriterion;
import tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

import static tdk_enum.common.IO.WhenToPrint.NEVER;
import static tdk_enum.graph.separators.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.graph.triangulation.TriangulationScoringCriterion.NONE;
import static tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.MCS_M;

public class ParallelResultHandlerFactory implements IResultHandlerFactory {
    IResultHandler resultHandler;

    @Override
    public IResultHandler produce() {
        if(resultHandler == null)
        {
            resultHandler =  inject(new ParallelResultHandler());
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
        resultHandler.createSummaryFile();
        resultHandler.setWhenToPrint(WhenToPrint.valueOf(TDEnumFactory.getProperties().getProperty("print", NEVER.name())));



        return  resultHandler;
    }

}
