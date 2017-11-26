package tdenum.graph.independent_set.single_thread.loggable;

import tdenum.common.IO.logger.LogInformation.LogInfoDuplicatedMIS;
import tdenum.common.IO.logger.Logger;
import tdenum.graph.independent_set.single_thread.improvements.ImprovedMaximalIndependentSetsEnumerator;

import java.util.HashSet;
import java.util.Set;

public class ImprovedLoggableMaximalIndependentSetsEnumerator<T> extends ImprovedMaximalIndependentSetsEnumerator<T> {


    @Override
    protected boolean extendSetInDirectionOfNode(final Set<T> s, final T node)
    {
//        System.out.println("Extending set " + s + " in direction of node" + node);
        Set<T> baseNodes = new HashSet<>();
        baseNodes.add(node);
        for (T t : s)
        {
            if(!graph.hasEdge(node, t))
            {
                baseNodes.add(t);
            }
        }

        Set<T> result = extender.extendToMaxIndependentSet(baseNodes);

        Logger.addSetToExtend(baseNodes);
        Logger.logResultData(result, s, node, baseNodes);

        return newSetFound(result);
    }

    @Override
    protected boolean newSetFound(final Set<T> generatedSet)
    {

        if (!P.contains(generatedSet))
        {
            if(setsNotExtended.add(generatedSet))
            {
                Q.setWeight(generatedSet, scorer.scoreIndependentSet(generatedSet));
                nextIndependentSet = generatedSet;
                nextSetReady = true;
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
