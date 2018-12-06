package tdk_enum.factories.enumeration.minimal_triangulator_factory;

import tdk_enum.common.Utils;
import tdk_enum.common.configuration.config_types.MinimalTriangulatorType;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.enumerators.triangulation.minimal_triangulators.IMinimalTriangulator;
import tdk_enum.enumerators.triangulation.minimal_triangulators.MinimalTriangulator;
import tdk_enum.enumerators.triangulation.minimal_triangulators.RandomMinimalTriangulator;
import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;

import static tdk_enum.common.configuration.config_types.MinimalTriangulatorType.BASELINE;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;

public class MinimalTriangulatorFactory implements IMinimalTriangulatorFactory {
    @Override
    public IMinimalTriangulator produce() {

        MinimalTriangulatorType triangulatorType = (MinimalTriangulatorType) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "minimalTriangulatorType", BASELINE);
        //MinimalTriangulatorType triangulatorType = MinimalTriangulatorType.valueOf(TDKEnumFactory.getProperties().getProperty("minTriType"));
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
        minimalTriangulator.setHeuristic((TriangulationAlgorithm) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "triangulationAlgorithm", MCS_M));

       // minimalTriangulator.setHeuristic(TriangulationAlgorithm.valueOf(TDKEnumFactory.getProperties().getProperty("alg",MCS_M.name())));
        return minimalTriangulator;
    }
}
