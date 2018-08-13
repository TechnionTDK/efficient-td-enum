package tdk_enum.enumerators.independent_set.single_thread.loggable;

import tdk_enum.common.IO.logger.LogInformation.LogInfoDuplicatedMIS;
import tdk_enum.common.IO.logger.Logger;
import tdk_enum.enumerators.independent_set.single_thread.improvements.ImprovedMaximalIndependentSetsEnumerator;

import java.util.Set;

public class ImprovedLoggableMaximalIndependentSetsEnumerator<T> extends ImprovedMaximalIndependentSetsEnumerator<T> {




    @Override
    protected boolean stepByStepTryGenerateNewResult(final T node, final Set<T> s )
    {


        Set<T> baseNodes = manipulateNodeAndResult(node, s);
        Set<T> result = generator.generateNew(graph,baseNodes);
        Logger.addSetToExtend(baseNodes);
        Logger.logResultData(result, s, node, baseNodes);

        return ( newStepByStepResultFound(result));


    }

    @Override
    protected void tryGenerateNewResult(final T node, final Set<T> s )
    {
        Set<T> baseNodes = manipulateNodeAndResult(node, s);
        Set<T> result = generator.generateNew(graph,baseNodes);
        Logger.addSetToExtend(baseNodes);
        Logger.logResultData(result, s, node, baseNodes);
        newResultFound(result);
    }



    @Override
    protected void newResultFound(final Set<T> generatedSet)
    {

        if (!P.contains(generatedSet))
        {

            if( Q.add(generatedSet))
            {
                resultPrinter.print(generatedSet);

            }
            else
            {
                Logger.addMISDuplication(generatedSet, LogInfoDuplicatedMIS.MIS_DUPLICATION_HIT.SETS_NOT_EXTENDED);

            }
        }
        else
        {
            Logger.addMISDuplication(generatedSet, LogInfoDuplicatedMIS.MIS_DUPLICATION_HIT.SETS_EXTENDED);
        }

    }

    @Override
    protected  boolean newStepByStepResultFound(final Set<T> generatedSet)
    {
        if (!P.contains(generatedSet))
        {

            if( Q.add(generatedSet))
            {
                resultPrinter.print(generatedSet);
                nextResult = generatedSet;
                nextResultReady = true;
                return true;
            }
            else
            {
                Logger.addMISDuplication(generatedSet, LogInfoDuplicatedMIS.MIS_DUPLICATION_HIT.SETS_NOT_EXTENDED);

            }
        }
        else
        {
            Logger.addMISDuplication(generatedSet, LogInfoDuplicatedMIS.MIS_DUPLICATION_HIT.SETS_EXTENDED);
        }
        return false;
    }

}
