package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.node.predicate.GameStatePredicate;
import dev.punchcafe.vngine.old.OldGameState;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class StateDeterminedNextNodeStrategy implements NextNodeStrategy {

    @Builder
    @Getter
    public static class Branch {
        GameStatePredicate predicate;
        // TODO: consider if this is easier with a nodeId Repository concept.
        // Or something injected by a node factory
        Node node;
    }

    private final GameState gameState;
    private final List<Branch> branches;

    @Override
    public Node getNextNode() {
        for(final var branch : branches){
            if(branch.getPredicate().evaluate(gameState)){
                return branch.getNode();
            }
        }
        throw new RuntimeException();
    }

}
