package dev.punchcafe.vngine.parse.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GameConfig {
    @JsonProperty("game-state-variables")
    private Map<String, VariableTypes> gameStateVariables;
    private List<ChapterConfig> chapters;
    //TODO: add chapter here
}
