package tdenum.factories.minimal_triangulator_factory;

import tdenum.TDEnum;
import tdenum.factories.TDEnumFactory;
import tdenum.graph.triangulation.minimal_triangulators.IMinimalTriangulator;
import tdenum.graph.triangulation.minimal_triangulators.MinimalTriangulator;
import tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

import static tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.MCS_M;

public class MinimalTriangulatorFactory implements IMinimalTriangulatorFactory {
    @Override
    public IMinimalTriangulator produce() {
        return  inject(new MinimalTriangulator());

    }

    IMinimalTriangulator inject(IMinimalTriangulator minimalTriangulator)
    {
        minimalTriangulator.setHeuristic(TriangulationAlgorithm.valueOf(TDEnumFactory.getProperties().getProperty("alg",MCS_M.name())));
        return minimalTriangulator;
    }
}
