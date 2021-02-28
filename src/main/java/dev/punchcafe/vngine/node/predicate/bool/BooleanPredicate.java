package dev.punchcafe.vngine.node.predicate.bool;

import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.node.predicate.GameStatePredicate;
import dev.punchcafe.vngine.node.predicate.PredicateValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BooleanPredicate implements GameStatePredicate {

    public enum Operation {
        IS, ISNT
    }

    private PredicateValue<Boolean> leftHandSide;
    private PredicateValue<Boolean> rightHandSide;
    private Operation operation;

    @Override
    public boolean evaluate(GameState gameState) {
        switch (operation){
            case IS:
                return leftHandSide.getValue(gameState) == rightHandSide.getValue(gameState);
            case ISNT:
                return leftHandSide.getValue(gameState) != rightHandSide.getValue(gameState);
            default:
                throw new UnsupportedOperationException();
        }
    }
}
