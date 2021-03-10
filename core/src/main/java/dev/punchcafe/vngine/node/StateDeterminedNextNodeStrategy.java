package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.node.predicate.GameStatePredicate;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class StateDeterminedNextNodeStrategy implements NextNodeStrategy {

    @Builder
    @Getter
    public static class Branch {
        private final GameStatePredicate predicate;
        private final Node node;
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
