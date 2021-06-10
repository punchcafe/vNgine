package dev.punchcafe.vngine.config.yaml;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class ChapterConfig {

    private String chapterId;
    private String firstNodeId;
    private List<Node> nodes;
    private Map<String, VariableTypes> chapterVariables;
}
