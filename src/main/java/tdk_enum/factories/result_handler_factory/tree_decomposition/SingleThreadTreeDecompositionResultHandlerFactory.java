package tdk_enum.factories.result_handler_factory.tree_decomposition;

import tdk_enum.common.IO.result_handler.IResultHandler;
import tdk_enum.common.IO.result_handler.tree_decomposition.single_thread.SingleThreadTreeDecompositionResultHandler;
import tdk_enum.common.Utils;
import tdk_enum.common.configuration.TDKTreeDecompositionEnumConfiguration;
import tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion;
import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;
import tdk_enum.common.configuration.config_types.TriangulationScoringCriterion;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.result_handler_factory.IResultHandlerFactory;

import static tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion.UNIFORM;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;
import static tdk_enum.common.configuration.config_types.TriangulationScoringCriterion.NONE;

public class SingleThreadTreeDecompositionResultHandlerFactory implements IResultHandlerFactory {
    @Override
    public IResultHandler produce() {
        return inject(new SingleThreadTreeDecompositionResultHandler());
    }

    private IResultHandler inject(SingleThreadTreeDecompositionResultHandler resultHandler) {
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

        resultHandler.setEnumeratorType(getEnumeratorString());

        resultHandler.setThreadNumber((((TDKTreeDecompositionEnumConfiguration) TDKEnumFactory.getConfiguration()).getThreadNumder()));




        return  resultHandler;
    }

    private String getEnumeratorString() {
        StringBuilder sb = new StringBuilder();
        sb.append(((TDKTreeDecompositionEnumConfiguration) TDKEnumFactory.getConfiguration()).getSingleThreadMISEnumeratorType());
        return sb.toString();
    }
}
