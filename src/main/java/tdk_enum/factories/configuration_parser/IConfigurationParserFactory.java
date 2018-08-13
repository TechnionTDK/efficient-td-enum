package tdk_enum.factories.configuration_parser;

import tdk_enum.common.configuration.configuration_parsers.IConfigurationParser;
import tdk_enum.factories.IFactory;

public interface IConfigurationParserFactory extends IFactory<IConfigurationParser> {

    IConfigurationParser produce(String fileName);
}
