package tdk_enum.factories.sets_extender_factory;

import tdk_enum.factories.TDEnumFactory;
import tdk_enum.graph.independent_set.set_extender.IIndependentSetExtender;
import tdk_enum.graph.independent_set.set_extender.single_thread.IndSetExtBySeparators;
import tdk_enum.graph.independent_set.set_extender.single_thread.IndSetExtByTriangulation;
import tdk_enum.graph.independent_set.set_extender.single_thread.loggable.LoggableIndSetExtBySeparators;
import tdk_enum.graph.independent_set.set_extender.single_thread.loggable.LoggableIndSetExtByTriangulation;
import tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

import static tdk_enum.factories.sets_extender_factory.SetsExtenderType.LOGGABLE;
import static tdk_enum.factories.sets_extender_factory.SetsExtenderType.VANILLA;
import static tdk_enum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.SEPARATORS;

public class SetsExtenderFactory implements ISetsExtenderFactory {
    @Override
    public IIndependentSetExtender produce() {
        if (SetsExtenderType.valueOf(TDEnumFactory.getProperties().getProperty("setExt", VANILLA.name())).equals(LOGGABLE))
        {
            return produceLoggableIndependentSetExtender();
        }
        return produceVanillaIndependentSetExtender();

    }

    IIndependentSetExtender produceVanillaIndependentSetExtender()
    {
        if (TriangulationAlgorithm.valueOf(TDEnumFactory.getProperties().getProperty("alg")).equals(SEPARATORS))
        {
            return inject (new IndSetExtBySeparators());
        }
        return inject(new IndSetExtByTriangulation());
    }


    IIndependentSetExtender produceLoggableIndependentSetExtender()
    {
        if (TriangulationAlgorithm.valueOf(TDEnumFactory.getProperties().getProperty("alg")).equals(SEPARATORS))
        {
            return  inject(new LoggableIndSetExtBySeparators());
        }
        else
        {
            return inject(new LoggableIndSetExtByTriangulation());
        }
    }



    IIndependentSetExtender inject(IIndependentSetExtender extender)
    {
        extender.setGraph(TDEnumFactory.getGraph());
        extender.setTriangulator(TDEnumFactory.getMinimalTriangulatorFactory().produce());
        return extender;
    }
}
