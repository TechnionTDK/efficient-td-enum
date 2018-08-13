package tdk_enum.factories.sets_extender_factory;

import tdk_enum.common.Utils;
import tdk_enum.common.configuration.config_types.SetsExtenderType;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.enumerators.independent_set.set_extender.IIndependentSetExtender;
import tdk_enum.enumerators.independent_set.set_extender.single_thread.IndSetExtBySeparators;
import tdk_enum.enumerators.independent_set.set_extender.single_thread.IndSetExtByTriangulation;
import tdk_enum.enumerators.independent_set.set_extender.single_thread.loggable.LoggableIndSetExtBySeparators;
import tdk_enum.enumerators.independent_set.set_extender.single_thread.loggable.LoggableIndSetExtByTriangulation;
import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;
import tdk_enum.factories.minimal_triangulator_factory.MinimalTriangulatorFactory;

import static tdk_enum.common.configuration.config_types.SetsExtenderType.LOGGABLE;
import static tdk_enum.common.configuration.config_types.SetsExtenderType.VANILLA;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.MCS_M;
import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.SEPARATORS;

public class SetsExtenderFactory implements ISetsExtenderFactory {
    @Override
    public IIndependentSetExtender produce() {

        if(Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "setsExtenderType", VANILLA).equals(LOGGABLE))
        //if (SetsExtenderType.valueOf(TDKEnumFactory.getProperties().getProperty("setExt", VANILLA.name())).equals(LOGGABLE))
        {
            return produceLoggableIndependentSetExtender();
        }
        return produceVanillaIndependentSetExtender();

    }

    IIndependentSetExtender produceVanillaIndependentSetExtender()
    {
        TriangulationAlgorithm algorithm = (TriangulationAlgorithm) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "triangulationAlgorithm", MCS_M);
        System.out.println("Producing Independent Set Extender. Algorithm: " + algorithm);
        if(algorithm.equals(SEPARATORS))
        //if (TriangulationAlgorithm.valueOf(TDKEnumFactory.getProperties().getProperty("alg")).equals(SEPARATORS))
        {

            return inject (new IndSetExtBySeparators());
        }
        return inject(new IndSetExtByTriangulation());
    }


    IIndependentSetExtender produceLoggableIndependentSetExtender()
    {
        TriangulationAlgorithm algorithm = (TriangulationAlgorithm) Utils.getFieldValue(TDKEnumFactory.getConfiguration(), "triangulationAlgorithm", MCS_M);
        System.out.println("Producing Loggable Independent Set Extender. Algorithm: " + algorithm);
        if(algorithm.equals(SEPARATORS))
       //if (TriangulationAlgorithm.valueOf(TDKEnumFactory.getProperties().getProperty("alg")).equals(SEPARATORS))
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
        extender.setGraph(TDKEnumFactory.getGraph());
        extender.setTriangulator(new MinimalTriangulatorFactory().produce());
        return extender;
    }
}
