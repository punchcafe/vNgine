package dev.punchcafe.vngine.node.predicate;

import dev.punchcafe.vngine.game.GameState;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimpleValue<T> implements PredicateValue<T> {

    private T value;

    @Override
    public T getValue(final GameState gameState) {
        return value;
    }

    @Override
    public <R> R acceptVisitor(GameStatePredicateVisitor<R> visitor) {
        return visitor.visitSimpleValue(this);
    }
}
