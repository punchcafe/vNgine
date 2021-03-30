package dev.punchcafe.vngine.node.predicate.integer;

import dev.punchcafe.vngine.state.StateContainer;
import dev.punchcafe.vngine.node.predicate.GameStatePredicateVisitor;
import dev.punchcafe.vngine.node.predicate.PredicateValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IntegerVariableValue implements PredicateValue<Integer> {

    private final String variableName;


    @Override
    public Integer getValue(final StateContainer gameState) {
        return gameState.getIntegerProperty(variableName);
    }

    @Override
    public <R> R acceptVisitor(GameStatePredicateVisitor<R> visitor) {
        return visitor.visitIntegerVariableValue(this);
    }
}
