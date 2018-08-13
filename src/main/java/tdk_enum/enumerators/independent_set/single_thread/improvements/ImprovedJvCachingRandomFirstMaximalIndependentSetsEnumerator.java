package tdk_enum.enumerators.independent_set.single_thread.improvements;

import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.enumerators.independent_set.set_extender.IIndependentSetExtender;
import tdk_enum.enumerators.triangulation.minimal_triangulators.RandomMinimalTriangulator;
import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;
import tdk_enum.factories.sets_extender_factory.SetsExtenderFactory;

import java.util.HashSet;
import java.util.Set;

public class ImprovedJvCachingRandomFirstMaximalIndependentSetsEnumerator<T>
        extends ImprovedRandomFirstMaximalIndependentSetsEnumerator<T> {


    double threash = 0.51;
    Set<Set<T>> generated = new HashSet<>();
    Set<Set<T>> reapearences = new HashSet<>();
    IIndependentSetExtender randExtender = new SetsExtenderFactory().produce();
    boolean finishRandPhase = false;

    public ImprovedJvCachingRandomFirstMaximalIndependentSetsEnumerator()
    {
        randExtender.setTriangulator(new RandomMinimalTriangulator(TriangulationAlgorithm.MCS_M));

    }


    @Override
    protected boolean stepByStepTryGenerateNewResult(final T node, final Set<T> s )
    {


        Set<T> baseNodes = manipulateNodeAndResult(node, s);
        if(!jvCache.add(baseNodes))
            return false;
        Set<T> result = generator.generateNew(graph,baseNodes);


        return ( newStepByStepResultFound(result));


    }

    @Override
    protected void tryGenerateNewResult(final T node, final Set<T> s )
    {
        Set<T> baseNodes = manipulateNodeAndResult(node, s);
        if(!jvCache.add(baseNodes))
            return;
        Set<T> result = generator.generateNew(graph,baseNodes);

        newResultFound(result);
    }



}
