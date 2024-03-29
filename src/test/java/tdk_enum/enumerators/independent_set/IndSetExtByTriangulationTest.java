package tdk_enum.enumerators.independent_set;

import org.junit.Before;
import org.junit.Test;
import tdk_enum.common.configuration.config_types.RunningMode;
import tdk_enum.common.IO.GraphReader;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.graph.TestsUtils;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.data_structures.Node;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.enumerators.independent_set.set_extender.single_thread.IndSetExtByTriangulation;
import tdk_enum.enumerators.triangulation.minimal_triangulators.MinimalTriangulator;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static tdk_enum.common.configuration.config_types.RunningMode.SINGLE_THREAD;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;

public class IndSetExtByTriangulationTest {

    @Before
    public void checkLinear()
    {
        IGraph g = GraphReader.read("C:\\tddatasets\\Datasets\\Easy\\BN\\CSP\\54.wcsp.uai");

        TDKEnumFactory.init(g);
      //  assumeTrue(RunningMode.valueOf(TDKEnumFactory.getProperties().getProperty("mode")).equals(SINGLE_THREAD));
    }


    @Test
    public void testCircleGraph()
    {
        IGraph g = TestsUtils.circleGraph(5);
        IndSetExtByTriangulation extender = new IndSetExtByTriangulation(g, new MinimalTriangulator(MCS_M));
        Set<MinimalSeparator> seps = new HashSet<>();
        Set<MinimalSeparator> result = extender.extendToMaxIndependentSet(seps);
        assertEquals(2, result.size());
        Iterator<MinimalSeparator> it = result.iterator();
        assertEquals(2, it.next().size());
        assertEquals(2, it.next().size());

        MinimalSeparator sep1 = new MinimalSeparator();
        sep1.add(new Node(1));
        sep1.add(new Node(3));

        seps.add(sep1);

        result = extender.extendToMaxIndependentSet(seps);

        MinimalSeparator exp1 = new MinimalSeparator();
        exp1.add(new Node(1));
        exp1.add(new Node(4));

        MinimalSeparator exp2 = new MinimalSeparator();
        exp2.add(new Node(0));
        exp2.add(new Node(3));

        assertEquals(1, findFrequency(exp1, result)+findFrequency(exp2, result));
        seps.add(exp1);
        result = extender.extendToMaxIndependentSet(seps);

        assertEquals(seps, result);

    }

    @Test
    public void testCliqueGraph()
    {
        IGraph g = TestsUtils.cliqueGraph(3);
        Set<MinimalSeparator> seps = new HashSet<>();
        IndSetExtByTriangulation extender = new IndSetExtByTriangulation(g, new MinimalTriangulator(MCS_M));
        Set<MinimalSeparator> result = extender.extendToMaxIndependentSet(seps);
        assertEquals(0, result.size());
    }

    @Test
    public void testLongNExtEightGraph()
    {
        IGraph g = TestsUtils.longNeckEightGraph();
        Set<MinimalSeparator> seps = new HashSet<>();
        IndSetExtByTriangulation extender = new IndSetExtByTriangulation(g, new MinimalTriangulator(MCS_M));
        Set<MinimalSeparator> result = extender.extendToMaxIndependentSet(seps);
        assertEquals(4, result.size());
    }



    <T> int  findFrequency(T obj, Collection<T> collection)
    {
        return Collections.frequency(collection, obj);
    }

}