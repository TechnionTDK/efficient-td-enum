package tdk_enum.common.configuration.configuration_parsers;

import tdk_enum.common.configuration.TDKEnumConfiguration;

import java.util.ArrayList;

public interface IConfigurationParser {

    public abstract ArrayList<TDKEnumConfiguration> parseConfigFile(String configFilePath);
    ArrayList<TDKEnumConfiguration> parseConfigFile();
}
