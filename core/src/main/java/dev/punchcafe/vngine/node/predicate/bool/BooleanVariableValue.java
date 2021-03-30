package dev.punchcafe.vngine.node.predicate.bool;

import dev.punchcafe.vngine.state.StateContainer;
import dev.punchcafe.vngine.node.predicate.GameStatePredicateVisitor;
import dev.punchcafe.vngine.node.predicate.PredicateValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BooleanVariableValue implements PredicateValue<Boolean> {

    private final String variableName;


    @Override
    public Boolean getValue(final StateContainer gameState) {
        return gameState.getBooleanProperty(variableName);
    }

    @Override
    public <R> R acceptVisitor(GameStatePredicateVisitor<R> visitor) {
        return visitor.visitBooleanVariableValue(this);
    }
}
