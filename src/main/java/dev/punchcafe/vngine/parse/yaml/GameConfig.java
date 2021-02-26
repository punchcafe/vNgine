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
    @JsonProperty("chapter-variables")
    private Map<String, VariableTypes> chapterVariables;
    private List<Node> nodes;
}
