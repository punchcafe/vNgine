package dev.punchcafe.vngine.node.predicate.integer;

import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.node.predicate.GameStatePredicate;
import dev.punchcafe.vngine.node.predicate.PredicateValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntegerComparisonPredicate implements GameStatePredicate {

    public enum Comparison {
        LESS_THAN, MORE_THAN, EQUALS
    }

    private PredicateValue<Integer> leftHandSide;
    private PredicateValue<Integer> rightHandSide;
    private Comparison comparison;


    @Override
    public boolean evaluate(GameState gameState) {
        switch (comparison){
            case EQUALS:
                return leftHandSide.getValue(gameState).equals(rightHandSide.getValue(gameState));
            case LESS_THAN:
                return leftHandSide.getValue(gameState) < rightHandSide.getValue(gameState);
            case MORE_THAN:
                return leftHandSide.getValue(gameState) > rightHandSide.getValue(gameState);
            default:
                throw new UnsupportedOperationException();
        }
    }
}
