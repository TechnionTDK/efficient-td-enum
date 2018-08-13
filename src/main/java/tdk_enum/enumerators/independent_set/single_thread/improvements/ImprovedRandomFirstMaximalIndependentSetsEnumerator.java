package tdk_enum.enumerators.independent_set.single_thread.improvements;

import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.enumerators.common.AlgorithmStep;
import tdk_enum.enumerators.independent_set.set_extender.IIndependentSetExtender;
import tdk_enum.enumerators.triangulation.minimal_triangulators.RandomMinimalTriangulator;
import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;
import tdk_enum.factories.sets_extender_factory.SetsExtenderFactory;

import java.util.HashSet;
import java.util.Set;

import static tdk_enum.enumerators.common.AlgorithmStep.BEGINNING;

public class ImprovedRandomFirstMaximalIndependentSetsEnumerator<T> extends ImprovedMaximalIndependentSetsEnumerator<T> {

    double threash = 0.51;
    Set<Set<T>> generated = new HashSet<>();
    Set<Set<T>> reapearences = new HashSet<>();
    IIndependentSetExtender randExtender = new SetsExtenderFactory().produce();
    boolean finishRandPhase = false;

    public ImprovedRandomFirstMaximalIndependentSetsEnumerator()
    {
        randExtender.setTriangulator(new RandomMinimalTriangulator(TriangulationAlgorithm.MCS_M));

    }




    @Override
    protected boolean runStepByStepFullEnumeration()
    {
        if (step == AlgorithmStep.BEGINNING)
        {
            if (!finishRandPhase)
            {
                Set<T> result = randExtender.extendToMaxIndependentSet(new HashSet());

                while(!P.add(result) && !finishCondition())
                {
                    reapearences.add(result);
                    result = randExtender.extendToMaxIndependentSet(new HashSet());
                }

                if (finishCondition())
                {
                    return false;
                }
//                long start = System.nanoTime();
                if (((double) reapearences.size()) / (P.size()+1) > threash)
                {
                    finishRandPhase = true;
                    System.out.println("converged after producing " + P.size() + " and " + reapearences.size() + " duplications");
                }
//                long finish = System.nanoTime() - start;
//                finishRandPhase = true;
//                System.out.println("Random generation converged to " + 100*threash + "% after " +
//                        TimeUnit.NANOSECONDS.toSeconds( finish) + " seconds");
                nextResultReady = true;
                nextResult = result;
                return true;

            }
            else
            {
                return super.runStepByStepFullEnumeration();

            }

        }
        return super.runStepByStepFullEnumeration();
    }


    @Override
    public void stepByStepDoFirstStep()
    {
        newStepByStepResultFound(generator.generateNew(new HashSet<T>()));
        step = BEGINNING;
        P.addAll(Q);
    }


//    @Override
//    protected boolean runFullEnumeration()
//    {
//        if (step == AlgorithmStep.BEGINNING)
//        {
//            if (!finishRandPhase)
//            {
//
//                Set<T> result = randExtender.extendToMaxIndependentSet(new HashSet());
//
//                while(!P.add(result) && !finishCondition())
//                {
//                    reapearences.add(result);
//                    result = randExtender.extendToMaxIndependentSet(new HashSet());
//
//                }
//
//                if (finishCondition())
//                {
//                    return false;
//                }
////                long start = System.nanoTime();
//                if (((double) reapearences.size()) / (P.size()+1) > threash)
//                {
//                    finishRandPhase = true;
//                    System.out.println("converged after producing " + P.size() + " and " + reapearences.size() + " duplications");
//                }
////                long finish = System.nanoTime() - start;
////                finishRandPhase = true;
////                System.out.println("Random generation converged to " + 100*threash + "% after " +
////                        TimeUnit.NANOSECONDS.toSeconds( finish) + " seconds");
//                nextSetReady = true;
//                nextIndependentSet = result;
//                return true;
//
//            }
//            else
//            {
//                return super.runFullEnumeration();
//
//            }
//
//        }
//        return super.runFullEnumeration();
//    }
//
//
//    @Override
//    public void doFirstStep()
//    {
//        newSetFound(extender.extendToMaxIndependentSet(new HashSet<T>()));
//        step = BEGINNING;
//        P.addAll(setsNotExtended);
//    }
}
