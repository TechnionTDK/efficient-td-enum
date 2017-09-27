package tdenum.factories.sets_extender_factory;

import tdenum.TDEnum;
import tdenum.factories.TDEnumFactory;
import tdenum.graph.independent_set.set_extender.IIndependentSetExtender;
import tdenum.graph.independent_set.set_extender.single_thread.IndSetExtBySeparators;
import tdenum.graph.independent_set.set_extender.single_thread.IndSetExtByTriangulation;
import tdenum.graph.independent_set.set_extender.single_thread.loggable.LoggableIndSetExtBySeparators;
import tdenum.graph.independent_set.set_extender.single_thread.loggable.LoggableIndSetExtByTriangulation;
import tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm;

import static tdenum.factories.sets_extender_factory.SetsExtenderType.LOGGABLE;
import static tdenum.factories.sets_extender_factory.SetsExtenderType.VANILLA;
import static tdenum.graph.triangulation.minimal_triangulators.TriangulationAlgorithm.SEPARATORS;

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
