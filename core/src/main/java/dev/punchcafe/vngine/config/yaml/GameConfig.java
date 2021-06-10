package dev.punchcafe.vngine.config.yaml;

import dev.punchcafe.vngine.pom.model.ProjectObjectModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.punchcafe.vngine.config.yaml.ConversionUtils.convertPomVariableTypesMap;
import static dev.punchcafe.vngine.config.yaml.VariableTypes.convertFromPom;
import static java.util.stream.Collectors.toMap;

@Getter
@Setter
@Builder(toBuilder = true)
public class GameConfig {

    public static GameConfig parseFromPom(final ProjectObjectModel<?> pom){
        return GameConfig.builder()
                .gameStateVariables(convertPomVariableTypesMap(pom.getGameStateVariableConfig().getGameStateVariables()))
                .chapters(pom.getChapterConfigs().stream()
                        .map(ChapterConfig::parseFromPom)
                        .collect(Collectors.toList()))
                .build();
    }
    private Map<String, VariableTypes> gameStateVariables;
    private List<ChapterConfig> chapters;
}
