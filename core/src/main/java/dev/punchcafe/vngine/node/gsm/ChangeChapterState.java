package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.parse.yaml.ChapterConfig;
import dev.punchcafe.vngine.parse.yaml.VariableTypes;
import dev.punchcafe.vngine.state.GameState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;

@Builder
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class ChangeChapterState implements GameStateModification {

    private ChapterConfig chapterConfig;

    @Override
    public void modify(GameState gameState) {
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

    @Override
    public <T> T acceptVisitor(GameStateModificationVisitor<T> visitor) {
        return visitor.visitChangeChapterState(this);
    }
}
