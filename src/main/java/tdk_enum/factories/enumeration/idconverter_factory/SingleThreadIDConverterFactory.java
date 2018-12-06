package tdk_enum.factories.enumeration.idconverter_factory;

import tdk_enum.graph.data_structures.IDConverter;

public class SingleThreadIDConverterFactory implements IIDConverterFactory {
    IDConverter converter = null;

    @Override
    public IDConverter produce() {
        if(converter == null)
        {
            converter = new IDConverter();
        }
        return converter;
    }
}
