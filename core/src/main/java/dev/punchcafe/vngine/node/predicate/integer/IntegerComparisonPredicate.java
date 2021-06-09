package dev.punchcafe.vngine.node.predicate.integer;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;
import dev.punchcafe.vngine.node.predicate.GameStatePredicate;
import dev.punchcafe.vngine.node.predicate.GameStatePredicateVisitor;
import dev.punchcafe.vngine.node.predicate.PredicateValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IntegerComparisonPredicate implements GameStatePredicate {

    public enum Comparison {
        LESS_THAN, MORE_THAN, EQUALS
    }

    private final PredicateValue<Integer> leftHandSide;
    private final PredicateValue<Integer> rightHandSide;
    private final Comparison comparison;


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

    @Override
    public <T> T acceptVisitor(GameStatePredicateVisitor<T> visitor) {
        return visitor.visitIntegerPredicate(this);
    }
}
