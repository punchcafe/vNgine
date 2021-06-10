package dev.punchcafe.vngine.config.yaml;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder(toBuilder = true)
public class GameConfig {
    private Map<String, VariableTypes> gameStateVariables;
    private List<ChapterConfig> chapters;
}
