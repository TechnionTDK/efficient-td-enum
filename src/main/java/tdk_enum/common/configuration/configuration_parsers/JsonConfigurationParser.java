package tdk_enum.common.configuration.configuration_parsers;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import tdk_enum.common.configuration.*;
import tdk_enum.common.configuration.config_types.EnumerationType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JsonConfigurationParser extends AbstractConfigurationParser {


    public JsonConfigurationParser(String fileName) {
        super(fileName);
    }

    @Override
    public ArrayList<TDKEnumConfiguration> parseConfigFile(String configFilePath) {

        ArrayList<TDKEnumConfiguration> configurations = new ArrayList<>();
        Gson gson = new Gson();
        try (JsonReader jsonReader = new JsonReader(new FileReader(configFilePath))) {
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(jsonReader).getAsJsonArray();
            for (JsonElement jsonElement : jsonArray)
            {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                switch (EnumerationType.valueOf( jsonObject.get("enumerationType").getAsString()))
                {
                    case NICE_TD:
                        configurations.add(gson.fromJson(jsonElement, TDKNiceTreeDecompositionEnumConfiguration.class));
                        break;
                    case PROPER_TD:
                        configurations.add(gson.fromJson(jsonElement, TDKTreeDecompositionEnumConfiguration.class));
                        break;
                    case SEPARATORS:
                        configurations.add(gson.fromJson(jsonElement, TDKSeperatorsEnumConfiguration.class));
                        break;
                    case MINIMAL_TRIANGULATIONS:
                        configurations.add(gson.fromJson(jsonElement, TDKChordalGraphEnumConfiguration.class));
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configurations;
    }

    @Override
    public ArrayList<TDKEnumConfiguration> parseConfigFile() {
        return parseConfigFile(fileName);
    }
}
