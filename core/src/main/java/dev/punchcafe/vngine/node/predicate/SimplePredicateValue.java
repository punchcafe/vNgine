package dev.punchcafe.vngine.node.predicate;

import dev.punchcafe.vngine.state.StateContainer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimplePredicateValue<T> implements PredicateValue<T> {

    private T value;

    @Override
    public T getValue(final StateContainer gameState) {
        return value;
    }

    @Override
    public <R> R acceptVisitor(GameStatePredicateVisitor<R> visitor) {
        return visitor.visitSimpleValue(this);
    }
}
