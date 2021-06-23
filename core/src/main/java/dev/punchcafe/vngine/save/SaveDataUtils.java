package dev.punchcafe.vngine.save;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;

public class SaveDataUtils {
    public static void loadStateSnapshotIntoStateContainer(final StateSnapshot stateSnapshot,
                                                           final StateContainer gameState) {
        gameState.clearState();
        for (final var entry : stateSnapshot.getIntegerPropertyMap().entrySet()) {
            gameState.setIntegerProperty(entry.getKey(), entry.getValue());
        }
        for (final var entry : stateSnapshot.getBooleanPropertyMap().entrySet()) {
            gameState.setBooleanProperty(entry.getKey(), entry.getValue());
        }
        for (final var entry : stateSnapshot.getStringPropertyMap().entrySet()) {
            gameState.setStringProperty(entry.getKey(), entry.getValue());
        }
    }

    public static SavedGameState takeSavedGameStateSnapshot(final GameState gameState) {
        return SavedGameState.builder()
                .gameStateSnapshot(takeStateSnapshotFromContainer(gameState))
                .chapterStateSnapshot(takeStateSnapshotFromContainer(gameState.getChapterState()))
                .build();
    }

    private static StateSnapshot takeStateSnapshotFromContainer(final StateContainer container) {
        return StateSnapshot.builder()
                .integerPropertyMap(container.takeIntegerPropertySnapshot())
                .booleanPropertyMap(container.takeBooleanPropertySnapshot())
                .stringPropertyMap(container.takeStringPropertySnapshot())
                .build();
    }
}
