package tdenum.factories.minimal_triangulator_factory;

import tdenum.factories.TDEnumFactory;
import tdenum.graph.triangulation.minimal_triangulators.IMinimalTriangulator;
import tdenum.graph.triangulation.minimal_triangulators.MinimalTriangulator;
import tdenum.graph.triangulation.minimal_triangulators.RandomMinimalTriangulator;
import tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

import static tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.MCS_M;

public class MinimalTriangulatorFactory implements IMinimalTriangulatorFactory {
    @Override
    public IMinimalTriangulator produce() {

        MinimalTriangulatorType triangulatorType = MinimalTriangulatorType.valueOf(TDEnumFactory.getProperties().getProperty("minTriType"));
        switch (triangulatorType)
        {
            case BASELINE:
            {
                System.out.println("producing baseline minimal triangulator");
                return inject(new MinimalTriangulator());
            }
            case RANDOM:
            {
                System.out.println("producing randomized minimal triangulator");
                return inject(new RandomMinimalTriangulator());
            }
        }

        return  inject(new MinimalTriangulator());

    }

    IMinimalTriangulator inject(IMinimalTriangulator minimalTriangulator)
    {
        minimalTriangulator.setHeuristic(TriangulationAlgorithm.valueOf(TDEnumFactory.getProperties().getProperty("alg",MCS_M.name())));
        return minimalTriangulator;
    }
}
