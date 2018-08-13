package tdk_enum.common.configuration.configuration_parsers;

import tdk_enum.common.configuration.TDKEnumConfiguration;

import java.util.ArrayList;

public abstract class AbstractConfigurationParser implements IConfigurationParser {

    String fileName = "";

    AbstractConfigurationParser(String fileName)
    {
        this.fileName = fileName;
    }


}
