package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.config.yaml.ChapterConfig;
import dev.punchcafe.vngine.game.save.SaveDataUtils;
import dev.punchcafe.vngine.game.save.SavedGameState;
import dev.punchcafe.vngine.node.NodeUtils;
import dev.punchcafe.vngine.state.GameState;
import lombok.Builder;

@Builder
public class LoadSavedGameState implements GameStateModification {

    private final SavedGameState savedGameState;
    private final ChapterConfig chapterConfigOfSave;

    @Override
    public void modify(GameState gameState) {
        SaveDataUtils.loadStateSnapshotIntoStateContainer(savedGameState.getGameStateSnapshot(), gameState);
        NodeUtils.initialiseNewChapterState(gameState, chapterConfigOfSave);
        SaveDataUtils.loadStateSnapshotIntoStateContainer(savedGameState.getChapterStateSnapshot(), gameState);
    }

    @Override
    public <T> T acceptVisitor(GameStateModificationVisitor<T> visitor) {
        return null;
    }
}
