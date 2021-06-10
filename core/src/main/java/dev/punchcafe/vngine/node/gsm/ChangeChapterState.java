package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.config.yaml.ChapterConfig;
import dev.punchcafe.vngine.config.yaml.VariableTypes;
import dev.punchcafe.vngine.node.NodeUtils;
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

/**
 * This Game State Modification initialises a new set of chapter variables.
 */
@Builder
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class ChangeChapterState implements GameStateModification {

    private ChapterConfig chapterConfig;

    @Override
    public void modify(GameState gameState) {
        NodeUtils.initialiseNewChapterState(gameState, chapterConfig);
    }

    @Override
    public <T> T acceptVisitor(GameStateModificationVisitor<T> visitor) {
        return visitor.visitChangeChapterState(this);
    }
}
