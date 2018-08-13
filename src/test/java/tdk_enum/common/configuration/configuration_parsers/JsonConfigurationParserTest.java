package tdk_enum.common.configuration.configuration_parsers;

import org.junit.Test;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.common.configuration.TDKSeperatorsEnumConfiguration;

import java.util.List;

import static org.junit.Assert.*;

public class JsonConfigurationParserTest {


    @Test
    public void testRead()
    {
        String configFile = "config.json";

        JsonConfigurationParser configurationParser = new JsonConfigurationParser(configFile);
        List<TDKEnumConfiguration> configurations =  configurationParser.parseConfigFile(configFile);
        System.out.println(configurations);
    }



}