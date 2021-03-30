package dev.punchcafe.vngine.node.predicate.string;

import dev.punchcafe.vngine.state.StateContainer;
import dev.punchcafe.vngine.node.predicate.GameStatePredicateVisitor;
import dev.punchcafe.vngine.node.predicate.PredicateValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StringVariableValue implements PredicateValue<String> {

    private final String variableName;


    @Override
    public String getValue(final StateContainer gameState) {
        return gameState.getStringProperty(variableName);
    }

    @Override
    public <R> R acceptVisitor(GameStatePredicateVisitor<R> visitor) {
        return visitor.visitStringVariableValue(this);
    }
}
