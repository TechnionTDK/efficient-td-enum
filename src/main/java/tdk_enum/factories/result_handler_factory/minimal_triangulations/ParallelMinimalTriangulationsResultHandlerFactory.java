package tdk_enum.factories.result_handler_factory.minimal_triangulations;

import tdk_enum.common.Utils;
import tdk_enum.common.configuration.TDKChordalGraphEnumConfiguration;
import tdk_enum.common.configuration.TDKSeperatorsEnumConfiguration;
import tdk_enum.common.configuration.config_types.WhenToPrint;
import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.common.IO.result_handler.chordal_graph.parallel.ParallelChordalGraphResultHandler;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion;
import tdk_enum.common.configuration.config_types.TriangulationScoringCriterion;
import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;
import tdk_enum.factories.result_handler_factory.IResultHandlerFactory;

import static tdk_enum.common.configuration.config_types.WhenToPrint.NEVER;
import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.NONE;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;

public class ParallelMinimalTriangulationsResultHandlerFactory implements IResultHandlerFactory {
    IResultHandler resultHandler;

    @Override
    public IResultHandler produce() {
        return inject(new ParallelChordalGraphResultHandler());


    }

    IResultHandler inject(ParallelChordalGraphResultHandler resultHandler)
    {

        String algorithm = "mcs";
        Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "triangulationAlgorithm", MCS_M);
        TriangulationAlgorithm heuristic = (TriangulationAlgorithm) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "triangulationAlgorithm", MCS_M);
        TriangulationScoringCriterion trianguationsOrder = (TriangulationScoringCriterion) Utils.getFieldValue(TDKEnumFactory.getConfiguration(),"triangulationScoringCriterion", NONE );
        SeparatorsScoringCriterion separatorsOrder = (SeparatorsScoringCriterion) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "separatorsScoringCriterion", UNIFORM);

        if (heuristic!= MCS_M || trianguationsOrder!=NONE || separatorsOrder!=UNIFORM)
        {
            algorithm = new StringBuilder().append(heuristic.name()).append(".").append(trianguationsOrder.name()).
                    append(".").append(separatorsOrder.name()).toString();

        }
        resultHandler.setAlgorithm(algorithm);
        resultHandler.setGraph(TDKEnumFactory.getGraph());

        resultHandler.setFileNameAddition(TDKEnumFactory.getConfiguration().getFileName());

//        resultHandler.createDetailedOutput();
        resultHandler.createSummaryFile();
        resultHandler.setWhenToPrint(TDKEnumFactory.getConfiguration().getWhenToPrint());

        resultHandler.setThreadNumber((((TDKChordalGraphEnumConfiguration) TDKEnumFactory.getConfiguration()).getThreadNumder()));
        resultHandler.setEnumeratorType(getEnumeratorString());


        return  resultHandler;
    }

    private String getEnumeratorString() {
        StringBuilder sb = new StringBuilder();
        sb.append(((TDKChordalGraphEnumConfiguration) TDKEnumFactory.getConfiguration()).getParallelMISEnumeratorType());
        return sb.toString();
    }

}
