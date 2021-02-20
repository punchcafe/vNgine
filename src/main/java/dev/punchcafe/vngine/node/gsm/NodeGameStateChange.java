package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.GameState;

import java.util.List;

public class NodeGameStateChange {

    final private List<GameStateModification> modifications;

    public NodeGameStateChange(final List<GameStateModification> modifications){
        if(modifications == null){
            throw new NullPointerException();
        }
        this.modifications = modifications;
    }

    public GameState modify(final GameState initialState){
        var gameState = initialState;
        for(final var modification : modifications){
            gameState = modification.modify(gameState);
        }
        return gameState;
    }
}
