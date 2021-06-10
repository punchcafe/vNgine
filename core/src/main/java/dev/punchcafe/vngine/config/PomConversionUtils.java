package dev.punchcafe.vngine.config;

import dev.punchcafe.vngine.config.yaml.*;
import dev.punchcafe.vngine.pom.model.ProjectObjectModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.punchcafe.vngine.config.yaml.VariableTypes.*;
import static java.util.stream.Collectors.toMap;

public class PomConversionUtils {
    public static Branch mapFromPomBranch(final dev.punchcafe.vngine.pom.model.Branch pomBranch ){
        return Branch.builder()
                .nodeId(pomBranch.getNodeId())
                .predicateExpression(pomBranch.getPredicateExpression())
                .prompt(pomBranch.getPrompt())
                .build();
    }
    public static ChapterConfig parseFromPom(final dev.punchcafe.vngine.pom.model.ChapterConfig chapterPom) {
        return ChapterConfig.builder()
                .chapterId(chapterPom.getChapterId())
                .firstNodeId(chapterPom.getFirstNodeId())
                .chapterVariables(convertPomVariableTypesMap(chapterPom.getChapterVariables()))
                .nodes(chapterPom.getNodes().stream()
                        .map(PomConversionUtils::parseFromPomNode)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Map<String, VariableTypes> convertPomVariableTypesMap(
            final Map<String, dev.punchcafe.vngine.pom.model.VariableTypes> pomMap){
        return pomMap.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> convertFromPom(entry.getValue())));
    }
    public static GameConfig parseFromPom(final ProjectObjectModel<?> pom){
        return GameConfig.builder()
                .gameStateVariables(convertPomVariableTypesMap(pom.getGameStateVariableConfig().getGameStateVariables()))
                .chapters(pom.getChapterConfigs().stream()
                        .map(PomConversionUtils::parseFromPom)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Node parseFromPomNode(final dev.punchcafe.vngine.pom.model.Node pomNode) {
        return Node.builder()
                .id(pomNode.getId())
                .narrativeId(pomNode.getNarrativeId())
                .branches(convertListOfBranches(pomNode.getBranches()))
                .gameStateModifiers(pomNode.getGameStateModifiers())
                .type(NodeType.valueOf(pomNode.getType().toString()))
                .build();
    }

    private static List<Branch> convertListOfBranches(final List<dev.punchcafe.vngine.pom.model.Branch> branches){
        if(branches == null){
            return List.of();
        }
        return branches.stream()
                .map(PomConversionUtils::mapFromPomBranch)
                .collect(Collectors.toList());
    }

    public static VariableTypes convertFromPom(final dev.punchcafe.vngine.pom.model.VariableTypes variableTypes){
        switch (variableTypes){
            case INT:
                return INT;
            case STR:
                return STR;
            case BOOL:
                return BOOL;
            default:
                return null;
        }
    }
}
