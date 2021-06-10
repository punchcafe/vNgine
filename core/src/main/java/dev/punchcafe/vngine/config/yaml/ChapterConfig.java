package dev.punchcafe.vngine.config.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.punchcafe.vngine.config.yaml.ConversionUtils.convertPomVariableTypesMap;

@Getter
@Builder
public class ChapterConfig {

    public static ChapterConfig parseFromPom(final dev.punchcafe.vngine.pom.model.ChapterConfig chapterPom) {
        return ChapterConfig.builder()
                .chapterId(chapterPom.getChapterId())
                .firstNodeId(chapterPom.getFirstNodeId())
                .chapterVariables(convertPomVariableTypesMap(chapterPom.getChapterVariables()))
                .nodes(chapterPom.getNodes().stream()
                        .map(Node::parseFromPomNode)
                        .collect(Collectors.toList()))
                .build();
    }

    @JsonProperty("chapter-id")
    private String chapterId;
    private String firstNodeId;
    private List<Node> nodes;
    @JsonProperty("chapter-variables")
    private Map<String, VariableTypes> chapterVariables;
}
