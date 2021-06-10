package dev.punchcafe.vngine.config.yaml;

import java.util.Map;

import static dev.punchcafe.vngine.config.yaml.VariableTypes.convertFromPom;
import static java.util.stream.Collectors.toMap;

public class ConversionUtils {
    public static Map<String, VariableTypes> convertPomVariableTypesMap(
            final Map<String, dev.punchcafe.vngine.pom.model.VariableTypes> pomMap){
        return pomMap.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> convertFromPom(entry.getValue())));
    }
}
