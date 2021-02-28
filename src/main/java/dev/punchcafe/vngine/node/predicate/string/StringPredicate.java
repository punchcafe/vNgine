package dev.punchcafe.vngine.node.predicate.string;

import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.node.predicate.GameStatePredicate;
import dev.punchcafe.vngine.node.predicate.GameStatePredicateVisitor;
import dev.punchcafe.vngine.node.predicate.PredicateValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StringPredicate implements GameStatePredicate {

    public enum Operation {
        IS, ISNT
    }

    private PredicateValue<String> leftHandSide;
    private PredicateValue<String> rightHandSide;
    private Operation operation;

    @Override
    public boolean evaluate(GameState gameState) {
        switch (operation){
            case IS:
                return leftHandSide.getValue(gameState).equals(rightHandSide.getValue(gameState));
            case ISNT:
                return !leftHandSide.getValue(gameState).equals(rightHandSide.getValue(gameState));
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public <T> T acceptVisitor(GameStatePredicateVisitor<T> visitor) {
        return visitor.visitStringPredicate(this);
    }
}
