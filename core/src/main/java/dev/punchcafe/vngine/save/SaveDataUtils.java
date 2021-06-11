package dev.punchcafe.vngine.save;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;

public class SaveDataUtils {
    public static void loadStateSnapshotIntoStateContainer(final StateSnapshot stateSnapshot, final StateContainer gameState) {
        gameState.clearState();
        for (final var key : stateSnapshot.getIntegerPropertyMap().keySet()) {
            gameState.setIntegerProperty(key, 0);
        }
        for (final var key : stateSnapshot.getBooleanPropertyMap().keySet()) {
            gameState.setBooleanProperty(key, false);
        }
        //TODO: make this none a constant
        for (final var key : stateSnapshot.getStringPropertyMap().keySet()) {
            gameState.setStringProperty(key, "none");
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
