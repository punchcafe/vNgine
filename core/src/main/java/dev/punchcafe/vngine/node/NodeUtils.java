package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.config.yaml.ChapterConfig;
import dev.punchcafe.vngine.config.yaml.VariableTypes;
import dev.punchcafe.vngine.state.GameState;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;

public class NodeUtils {
    public static void initialiseNewChapterState(final GameState gameState, final ChapterConfig chapterConfig){
        final Map<VariableTypes, List<Map.Entry<String, VariableTypes>>> chapterStateVariableMap =
                chapterConfig.getChapterVariables().entrySet().stream().collect(groupingBy(Map.Entry::getValue));

        final List<String> integerVariableNames = ofNullable(chapterStateVariableMap.get(VariableTypes.INT))
                .orElse(List.of())
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        final List<String> booleanVariableNames = ofNullable(chapterStateVariableMap.get(VariableTypes.BOOL))
                .orElse(List.of())
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        final List<String> stringVariableNames = ofNullable(chapterStateVariableMap.get(VariableTypes.STR))
                .orElse(List.of())
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        gameState.initialiseNewChapterState(integerVariableNames, booleanVariableNames, stringVariableNames);
    }
}
