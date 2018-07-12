package tdk_enum.graph.independent_set.single_thread.improvements;

import tdk_enum.factories.TDEnumFactory;
import tdk_enum.enumerators.AlgorithmStep;
import tdk_enum.graph.independent_set.set_extender.IIndependentSetExtender;
import tdk_enum.graph.triangulation.minimal_triangulators.RandomMinimalTriangulator;
import tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

import java.util.HashSet;
import java.util.Set;

import static tdk_enum.enumerators.AlgorithmStep.BEGINNING;

public class ImprovedRandomFirstMaximalIndependentSetsEnumerator<T> extends ImprovedMaximalIndependentSetsEnumerator<T> {

    double threash = 0.51;
    Set<Set<T>> generated = new HashSet<>();
    Set<Set<T>> reapearences = new HashSet<>();
    IIndependentSetExtender randExtender = TDEnumFactory.getSetsExtenderFactory().produce();
    boolean finishRandPhase = false;

    public ImprovedRandomFirstMaximalIndependentSetsEnumerator()
    {
        randExtender.setTriangulator(new RandomMinimalTriangulator(TriangulationAlgorithm.MCS_M));

    }

    @Override
    protected boolean runFullEnumeration()
    {
        if (step == AlgorithmStep.BEGINNING)
        {
            if (!finishRandPhase)
            {

                Set<T> result = randExtender.extendToMaxIndependentSet(new HashSet());

                while(!P.add(result) && !timeLimitReached())
                {
                    reapearences.add(result);
                    result = randExtender.extendToMaxIndependentSet(new HashSet());

                }

                if (timeLimitReached())
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
                nextSetReady = true;
                nextIndependentSet = result;
                return true;

            }
            else
            {
                return super.runFullEnumeration();

            }

        }
        return super.runFullEnumeration();
    }


    @Override
    public void doFirstStep()
    {
        newSetFound(extender.extendToMaxIndependentSet(new HashSet<T>()));
        step = BEGINNING;
        P.addAll(setsNotExtended);
    }
}
