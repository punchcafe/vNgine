package dev.punchcafe.vngine.game.save;

import dev.punchcafe.vngine.state.StateContainer;

public class SaveDataUtils {
    public static void loadStateSnapshotIntoStateContainer(final StateSnapshot stateSnapshot, final StateContainer gameState){
        gameState.clearState();
        for(final var key : stateSnapshot.getIntegerPropertyMap().keySet()){
            gameState.setIntegerProperty(key, 0);
        }
        for(final var key : stateSnapshot.getBooleanPropertyMap().keySet()){
            gameState.setBooleanProperty(key, false);
        }
        //TODO: make this none a constant
        for(final var key : stateSnapshot.getStringPropertyMap().keySet()){
            gameState.setStringProperty(key, "none");
        }
    }
}
