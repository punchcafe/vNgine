package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.GameState;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class NodeGameStateChange {

    final private List<GameStateModification> modifications;

    public NodeGameStateChange(final @NonNull List<GameStateModification> modifications){
        this.modifications = modifications;
    }

    public void modify(final GameState gameState){
        for(final var modification : modifications){
            modification.modify(gameState);
        }
    }
}
