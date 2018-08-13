package tdk_enum.factories.configuration_parser;

import org.apache.commons.io.FilenameUtils;
import tdk_enum.common.configuration.configuration_parsers.IConfigurationParser;
import tdk_enum.common.configuration.configuration_parsers.JsonConfigurationParser;

import java.io.File;
import java.nio.file.Paths;

public class ConfigurationParserFactory implements IConfigurationParserFactory {
    @Override
    public IConfigurationParser produce() {
        return produce("config.json");
    }

    @Override
    public IConfigurationParser produce(String fileName) {
        File input = Paths.get(fileName).toFile();
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension)
        {
            case "json":
                return new JsonConfigurationParser(fileName);
            default:
                return new JsonConfigurationParser(fileName);
        }
    }
}
