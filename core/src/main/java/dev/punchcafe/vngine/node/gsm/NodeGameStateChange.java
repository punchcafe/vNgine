package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.state.GameState;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Builder
@Getter
public class NodeGameStateChange {

    public static NodeGameStateChange NO_STATE_CHANGE = new NodeGameStateChange(List.of());

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
